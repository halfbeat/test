package es.jcyl.cs.extractor.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

import es.jcyl.cs.extractor.exception.ControllerException;
import es.jcyl.cs.extractor.exception.MappingException;
import es.jcyl.cs.extractor.exception.PortException;
import es.jcyl.cs.extractor.mapper.AbstractMapper;
import es.jcyl.cs.extractor.mapper.AbstractSource;
import es.jcyl.cs.extractor.mapper.Controller;
import es.jcyl.cs.extractor.mapper.InputPort;
import es.jcyl.cs.extractor.mapper.InputStreamSource;
import es.jcyl.cs.extractor.mapper.OutputPort;
import es.jcyl.cs.extractor.mapper.OutputStreamSink;
import es.jcyl.cs.extractor.standarmappers.IntegerSource;
import es.jcyl.cs.extractor.standarmappers.MuxMapper;
import es.jcyl.cs.extractor.standarmappers.OneToMany;
import es.jcyl.cs.extractor.standarmappers.SwitchMapper;
import es.jcyl.cs.extractor.util.CircularArrayList;
import es.jcyl.cs.extractor.util.SimpleOutputWriter;
import es.jcyl.cs.extractor.variable.VariableGroupType;
import es.jcyl.cs.extractor.variable.VariableGroupValue;
import es.jcyl.cs.extractor.variable.VariableType;
import es.jcyl.cs.extractor.variable.VariableValue;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedDouble;
import es.jcyl.cs.extractor.variable.boxedtypes.BoxedInteger;

public class Extractor01Test extends TestCase {
	public final static Logger LOGGER = Logger.getLogger("Main");

	public Extractor01Test() {
		super("01");
	}

	public static Test suite() {
		return new TestSuite(Extractor01Test.class);
	}

	public void test01() throws Exception {
		Controller c = new Controller();

		IntegerSource is1 = new IntegerSource(0, 1);
		IntegerSource is2 = new IntegerSource(0, 2);
		OneToMany otm1 = new OneToMany(2);
		OneToMany otm2 = new OneToMany(2);
		IntegerSource is3 = new IntegerSource(10, 2);
		MuxMapper mm = new MuxMapper(4);
		SwitchMapper sm = new SwitchMapper(VariableType.VT_ANY, 2, 0);
		SimpleOutputWriter sow = new SimpleOutputWriter(System.err);

		c.link(is1, otm1);
		c.link(is2, otm2);

		c.link(otm1, 0, mm, 0);
		c.link(otm1, 1, mm, 1);
		c.link(otm2, 0, mm, 2);
		c.link(otm2, 1, mm, 3);

		c.link(mm, 0, sm, 0);
		c.link(is3, 0, sm, 1);

		c.link(sm, 0, sow, 0);

		int count = 10;
		while (count > 0) {
			count--;
			if (count == 4) {
				sm.switchInput(1);
			}
			c.run();
		}
	}

	public void test04() throws Exception {
		class X extends AbstractSource {
			VariableValue v = new VariableValue(VariableType.VT_INT);
			int i = 0;

			public X() throws PortException, ControllerException {
				super();
				OutputPort a = new OutputPort(VariableType.VT_INT);
				addOutput("Output0", a);
			}

			public boolean update() throws MappingException {
				if (i < 10) {
					OutputPort p = getOutput("Output0");
					if (p != null) {
						v.setIntValue(i++);
						p.write(v);
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			public boolean execute() throws MappingException {
				return true;
			}
		}

		class Y extends AbstractMapper {
			public Y() throws PortException, ControllerException {
				super();
				InputPort b = new InputPort(VariableType.VT_INT);
				addInput("Input0", b);
				OutputPort a = new OutputPort(VariableType.VT_INT);
				addOutput("Output0", a);
			}

			public boolean execute() throws MappingException {
				VariableValue v = getInput("Input0").read();
				v.setIntValue(v.asInteger() + 1);
				getOutput("Output0").write(v);
				return true;
			}
		}

		class Z extends AbstractMapper {
			public Z() throws PortException, ControllerException {
				super();
				InputPort a = new InputPort(VariableType.VT_INT);
				InputPort b = new InputPort(VariableType.VT_INT);
				addInput("Input0", a);
				addInput("Input1", b);
				OutputPort c = new OutputPort(VariableType.VT_INT);
				addOutput("Output0", c);
			}

			public boolean execute() throws MappingException {
				VariableValue v1 = getInput("Input0").read();
				VariableValue v2 = getInput("Input1").read();
				getOutput("Output0").write(
						new VariableValue(VariableType.VT_INT,
								new BoxedInteger(v1.asInteger()
										+ v2.asInteger())));
				return true;
			}
		}

		class M extends AbstractMapper {
			public final static int NUM_VALUES = 5;
			CircularArrayList cl = new CircularArrayList();

			public M() throws PortException, ControllerException {
				super();
				InputPort b = new InputPort(VariableType.VT_INT);
				addInput("Input0", b);
				OutputPort a = new OutputPort(VariableType.VT_DOUBLE);
				addOutput("Output0", a);
			}

			public boolean execute() throws MappingException {
				VariableValue v = getInput("Input0").read();
				if (v != null) {
					cl.add(v);
					double media = 0;
					if (cl.size() == NUM_VALUES) {
						for (int i = 0; i < NUM_VALUES; i++) {
							media = media
									+ (double) ((VariableValue) cl.get(i))
											.asInteger();
						}
						media = media / NUM_VALUES;
						VariableValue o = new VariableValue(
								VariableType.VT_DOUBLE, new BoxedDouble(media));
						getOutput("Output0").write(o);
						cl.remove(0);
					}
					return true;
				} else {
					return false;
				}
			}
		}

		Controller c = new Controller();
		X x = new X();
		x.setName("X");
		Y y1 = new Y();
		y1.setName("Y1");
		Y y2 = new Y();
		y2.setName("Y2");
		Z z = new Z();
		z.setName("Z");
		M m = new M();
		m.setName("M");
		SimpleOutputWriter w = new SimpleOutputWriter(System.err);
		w.setName("Writer");
		OneToMany otm = new OneToMany(2);

		c.link(x, 0, otm, 0);
		c.link(otm, 0, y1, 0);
		c.link(otm, 1, y2, 0);
		c.link(y1, 0, z, 0);
		c.link(y2, 0, z, 1);
		c.link(z, 0, m, 0);
		c.link(m, 0, w, 0);

		c.runAll();
	}

	public void test07() throws Exception {
		Controller c = new Controller();
		VariableGroupType vgt = new VariableGroupType();
		vgt.add(VariableType.VT_LONG);
		vgt.add(VariableType.VT_DOUBLE);
		vgt.add(VariableType.VT_DOUBLE);
		vgt.add(VariableType.VT_LONG);

		createTestFile("c:\\temp\\prueba.bin", 10, 10);

		InputStream fis = new BufferedInputStream(new FileInputStream(
				"c:\\temp\\prueba.bin"));
		OutputStream fos = new BufferedOutputStream(new FileOutputStream(
				"c:\\temp\\prueba_output.bin"));

		InputStreamSource iss = new InputStreamSource(vgt, fis);
		SimpleOutputWriter sow = new SimpleOutputWriter(System.out);
		OutputStreamSink oss = new OutputStreamSink(vgt, fos);
		//OneToMany otm = new OneToMany(2);
		c.link(iss, 0, sow, 0);
		//c.link(otm, 0, sow, 0);
		//c.link(otm, 1, oss, 0);
		System.out.println(c.runAll());

		fis.close();
		fos.close();
	}

	public void test09() throws Exception {
		Controller c = new Controller();
		IntegerSource s1 = new IntegerSource(10, 1);
		IntegerSource s2 = new IntegerSource(20, 1);
		MuxMapper mux = new MuxMapper(2);
		OneToMany otm = new OneToMany(2);
		SimpleOutputWriter w1 = new SimpleOutputWriter(System.out);
		SimpleOutputWriter w2 = new SimpleOutputWriter(System.err);

		c.link(s1, mux);
		c.link(s2, mux);
		c.link(mux, 0, otm, 0);
		c.link(otm, 0, w1, 0);
		c.link(otm, 1, w2, 0);
		System.out.println(c.runAll(1));
	}
	
	public void test10() throws Exception {
		VariableGroupType vgt1 = new VariableGroupType();
		vgt1.add(VariableType.VT_STRING);
		vgt1.add(VariableType.VT_INT);
		VariableGroupType vgt2 = new VariableGroupType();
		vgt2.add(vgt1);
		vgt2.add(vgt1);
		
		VariableGroupValue vgv = new VariableGroupValue(vgt2);
		
		vgv.getGroupValue(0).getValue(0).setStringValue("LAPERA");
		System.out.println(vgv);
		System.out.println(vgv.getType());
	}

	private static int generateInt(int min, int max) {
		Random r = new Random();
		return min + r.nextInt(max - min);
	}

	private static double generateDouble(double min, double max) {
		Random r = new Random();
		return min + r.nextDouble() * (max - min);
	}

	private static void createTestFile(String path, int count, int ubase)
			throws IOException {
		FileOutputStream fos = null;
		DataOutputStream dos = null;

		try {
			fos = new FileOutputStream(path);
			dos = new DataOutputStream(fos);
			for (int i = 0; i < count; i++) {
				long unidad = ubase++;
				long pt = generateInt(100, 2000);
				double ncit = generateDouble(0, 100);
				double ncct = generateDouble(0, 100);
				dos.writeLong(unidad);
				dos.writeDouble(ncit);
				dos.writeDouble(ncct);
				dos.writeLong(pt);
			}
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
}
