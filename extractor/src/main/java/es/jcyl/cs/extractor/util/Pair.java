package es.jcyl.cs.extractor.util;

public class Pair
{
  public Pair(Object f, Object s)
  { 
    first = f;
    second = s;   
  }

  public Object getFirst()
  {
    return first;
  }

  public Object getSecond() 
  {
    return second;
  }

  public String toString()
  { 
    return "(" + first.toString() + ", " + second.toString() + ")"; 
  }

  private Object first;
  private Object second;
}
