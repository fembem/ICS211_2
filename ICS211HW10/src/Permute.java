// Permute.java -- A class generating all permutations

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.reflect.Array;

/**
 * The Class Permute.
 * @author http://cs.fit.edu/~ryan/java/programs/combinations/Permute-java.html
 * 
 */
public class Permute implements Iterator {
  
   /** The size. */
   private final int size;
   
   /** The elements. */
   private final Object [] elements;  // copy of original 0 .. size-1
   
   /** The ar. */
   private final Object ar;           // array for output,  0 .. size-1
   
   /** The permutation. */
   private final int [] permutation;  // perm of nums 1..size, perm[0]=0

   /** The next. */
   private boolean next = true;

   // int[], double[] array won't work :-(
   /**
    * Instantiates a new permute.
    *
    * @param e the e
    */
   public Permute (Object [] e) {
      size = e.length;
      elements = new Object [size];    // not suitable for primitives
      System.arraycopy (e, 0, elements, 0, size);
      ar = Array.newInstance (e.getClass().getComponentType(), size);
      System.arraycopy (e, 0, ar, 0, size);
      permutation = new int [size+1];
      for (int i=0; i<size+1; i++) {
         permutation [i]=i;
      }
   }
      
   /**
    * Form next permutation.
    */
   private void formNextPermutation () {
      for (int i=0; i<size; i++) {
         // i+1 because perm[0] always = 0
         // perm[]-1 because the numbers 1..size are being permuted
         Array.set (ar, i, elements[permutation[i+1]-1]);
      }
   }

   /* (non-Javadoc)
    * @see java.util.Iterator#hasNext()
    */
   public boolean hasNext() {
      return next;
   }

   /* (non-Javadoc)
    * @see java.util.Iterator#remove()
    */
   public void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   /**
    * Swap.
    *
    * @param i the i
    * @param j the j
    */
   private void swap (final int i, final int j) {
      final int x = permutation[i];
      permutation[i] = permutation [j];
      permutation[j] = x;
   }

   // does not throw NoSuchElement; it wraps around!
   /* (non-Javadoc)
    * @see java.util.Iterator#next()
    */
   public Object next() throws NoSuchElementException {

      formNextPermutation ();  // copy original elements

      int i = size-1;
      while (permutation[i]>permutation[i+1]) i--;

      if (i==0) {
         next = false;
         for (int j=0; j<size+1; j++) {
            permutation [j]=j;
         }
         return ar;
      }

      int j = size;
      
      while (permutation[i]>permutation[j]) j--;
      swap (i,j);
      int r = size;
      int s = i+1;
      while (r>s) { swap(r,s); r--; s++; }

      return ar;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString () {
      final int n = Array.getLength(ar);
      final StringBuffer sb = new StringBuffer ("[");
      for (int j=0; j<n; j++) {
         sb.append (Array.get(ar,j).toString());
         if (j<n-1) sb.append (",");
      }
      sb.append("]");
      return new String (sb);
   }

   /**
    * The main method.
    *
    * @param args the arguments
    */
   public static void main (String [] args) {
      for (Iterator i = new Permute(args); i.hasNext(); ) {
         final String [] a = (String []) i.next();
         System.out.println (i);
      }
   }
}
