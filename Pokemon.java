// Pokemon.java
// ==================
// Inserting pokemon into treap.
// MethodHandles.lookup() trick.


import java.util.*;
import java.io.*;

public class Pokemon
{
	static Treap<String> generateTreap(Scanner in)
	{
		Treap<String> t = new Treap<>();

    in.useDelimiter(", ");

    while (in.hasNext())
    {
			String str = in.next();
      System.out.println("Inserting... " + str);
      t.add(str);
    }

		return t;
	}

	public static void main(String [] args) throws IOException
	{
		Scanner in = new Scanner(new File("Pokemon.txt"));
		Treap<String> t = generateTreap(in);

		// print treap stats
		System.out.println("Treap size (n): " + t.size());
		System.out.println("Treap height (h): " + t.height());
		System.out.println("Treap constant (hopefully) (c): " + t.height() / (Math.log10(t.size())));
	}
}
