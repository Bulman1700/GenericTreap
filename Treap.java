// Johnathon Bulman

// Treap.java
// ==========
// Generic Treap using the Comparable Interface.
// Supports add(), remove(), and contains() operations.

import java.util.*;

// Node class labeled 'Pokeball'.
class Pokeball<Pokemon>
{
  Pokemon data; // user data
  int priority; // randomly generated
  Pokeball<Pokemon> left, right; // references

  Pokeball(Pokemon data, int priority)
  {
    this.data = data;
    this.priority = priority;
  }
}

// Treap Class
// Holds generic 'P' data type inside the 'Pokeball'.
public class Treap<P extends Comparable<P>>
{
  // Don't jack up my root!
  private Pokeball<P> root;
  private Set<Integer> myHash; // used priorities
  private int size; // size of treap

  // Initialize the set.
  public Treap()
  {
    this.myHash = new HashSet<Integer>();
  }

  // Generate random priority method.
  private int randNum()
  {
    Random rand = new Random();

    int randInt = rand.nextInt(Integer.MAX_VALUE) + 1;

    // Checking that priority is random *and* unique.
    while(myHash.contains(randInt))
      randInt = rand.nextInt(Integer.MAX_VALUE) + 1;

    myHash.add(randInt);

    return randInt;
  }

  // Assumes root is non-null.
  private Pokeball<P> rotateLeft(Pokeball<P> root)
  {
    Pokeball<P> curr = root.right;

    root.right = curr.left;
    curr.left = root;

    return curr;
  }

  // Assumes root is non-null.
  private Pokeball<P> rotateRight(Pokeball<P> root)
  {
    Pokeball<P> curr = root.left;

    root.left = curr.right;
    curr.right = root;

    return curr;
  }

  // Calls private add() method.
  public void add(P data)
  {
    root = insert(root, data);
  }

  // Place 'data' in the Treap.
  private Pokeball<P> insert(Pokeball<P> root, P data)
  {
    if (root == null)
    {
      size++;
      return new Pokeball<>(data, randNum());
    }

    // Searching for position to place data..
    else if (data.compareTo(root.data) < 0)
    {
      root.left = insert(root.left, data);
      
      // Perform rotations if necessary.
      if (root.left.priority < root.priority)
        root = rotateRight(root);
    }

    else if (data.compareTo(root.data) > 0)
    {
      root.right = insert(root.right, data);
      
      // Perform rotations if necessary.
      if (root.right.priority < root.priority)
        root = rotateLeft(root);
    }

    // Exclude duplcate 'data' insertions.
    return root;
  }

  // Calls private rm() method.
  public void remove(P data)
  {
    root = rm(root, data);
  }

  // Removes 'data' from the Treap.
  private Pokeball<P> rm(Pokeball<P> root, P data)
  {
    if (root == null)
      return null;

    // Searching for 'data'..
    else if (data.compareTo(root.data) < 0)
      root.left = rm(root.left, data);
    
    else if (data.compareTo(root.data) > 0)
      root.right = rm(root.right, data);
    
    else // data found
    {
      // Checking for children and performing appropriate
      // maneuvers to remove Pokeball.
      if (root.left == null && root.right == null)
      {
        myHash.remove(root.priority);
        this.size--;

        return root = null;
      }

      else if (root.left == null)
      {
        root = rotateLeft(root);
        root.left = rm(root.left, data);
      }

      else if (root.right == null)
      {
        root = rotateRight(root);
        root.right = rm(root.right, data);
      }

      else // 2 children
      {
        if (root.right.priority > root.left.priority)
        {
          root = rotateRight(root);
          root.right = rm(root.right, data);
        }

        else
        {
          root = rotateLeft(root);
          root.left = rm(root.left, data);
        }
      }
    }

    return root;
  }

  // Calls private contains() method.
  public boolean contains(P data)
  {
    return contains(root, data);
  }

  // Searches for 'data' in the Treap.
  private boolean contains(Pokeball<P> root, P data)
  {
    if (root == null)
      return false;

    else if (data.compareTo(root.data) > 0)
      return contains(root.right, data);

    else if (data.compareTo(root.data) < 0)
      return contains(root.left, data);
      
    else
      return true;
  }

  // Returns size of Treap.
  public int size()
  {
    return this.size;
  }

  // Calls private H() method.
  public int height()
  {
    return H(root);
  }

  private int max(int a, int b)
  {
    return (a > b) ? a : b;
  }

  // Returns the height of the Treap.
  private int H(Pokeball<P> root)
  {
    if (root == null)
      return -1; // empty tree

    return 1 + max(H(root.left), H(root.right));
  }

  // Standard tree traversals:

  public void inorderTraversal()
  {
    if (root == null)
    {
      System.out.println("(Empty tree)");
      return;
    }

    System.out.println("In-order Traversal:");
    System.out.println("===================");
    inorder(root);
    System.out.println();
  }

  private void inorder(Pokeball<P> root)
  {
    if (root == null)
      return;

    inorder(root.left);
    System.out.print(root.data + "  ");
    inorder(root.right);
  }

  public void preorderTraversal()
  {
    if (root == null)
    {
      System.out.println("(Empty tree)");
      return;
    }

    System.out.println("Pre-order Traversal:");
    System.out.println("====================");
    preorder(root);
    System.out.println();
  }

  private void preorder(Pokeball<P> root)
  {
    if (root == null)
      return;

    System.out.print(root.data + "  ");
    preorder(root.left);
    preorder(root.right);
  }

  public void postorderTraversal()
  {
    if (root == null)
    {
      System.out.println("(Empty tree)");
      return;
    }

    System.out.println("Post-order Traversal:");
    System.out.println("=====================");
    postorder(root);
    System.out.println();
  }

  private void postorder(Pokeball<P> root)
  {
    if (root == null)
      return;

    postorder(root.left);
    postorder(root.right);
    System.out.print(root.data + "  ");
  }
}
