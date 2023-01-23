// Johnathon Bulman

// Treap.java
// ==========
// Generic Treap using the Comparable Interface.
// Supports add(), remove(), and contains() operations.

import java.util.*;

// Node class
class Node<Pokemon>
{
  Pokemon data; // user data
  int priority; // randomly generated
  Node<Pokemon> left, right;

  Node(Pokemon data, int priority)
  {
    this.data = data;
    this.priority = priority;
  }
}

// Treap Class
// Holds generic 'P' data type.
public class Treap<P extends Comparable<P>>
{
  // Don't jack up my root!
  private Node<P> root;
  private HashSet<Integer> myHash = new HashSet<Integer>(); // stores used priorities
  private int size; // size of treap

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
  private Node<P> rotateLeft(Node<P> root)
  {
    Node<P> curr = root.right;

    root.right = curr.left;
    curr.left = root;

    return curr;
  }

  // Assumes root is non-null.
  private Node<P> rotateRight(Node<P> root)
  {
    Node<P> curr = root.left;

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
  private Node<P> insert(Node<P> root, P data)
  {
    if (root == null)
    {
      this.size++; // new node => size++
      return new Node<>(data, randNum()); // New node with 'data' and random priority.
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

  // Calls private rm()
  public void remove(P data)
  {
    root = rm(root, data);
  }

  // Removes 'data' from the Treap.
  private Node<P> rm(Node<P> root, P data)
  {
    if (root == null)
      return null;

    // Searching for 'data'..
    else if (data.compareTo(root.data) < 0)
    {
      root.left = rm(root.left, data);
    }
    else if (data.compareTo(root.data) > 0)
    {
      root.right = rm(root.right, data);
    }
    else // data found
    {
      // Checking for children and performing appropriate
      // maneuvers to remove node.
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
  private boolean contains(Node<P> root, P data)
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
  private int H(Node<P> root)
  {
    if (root == null)
      return -1;

    return 1 + max(H(root.left), H(root.right));
  }

  // Tree traversal:

  public void inorder()
  {
    System.out.print("In-order Traversal:");
    inorder(root);
    System.out.println();
  }

  private void inorder(Node<P> root)
  {
    if (root == null)
      return;

    inorder(root.left);
    System.out.print(" " + root.data + ",");
    inorder(root.right);
  }
}
