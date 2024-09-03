import java.util.*;
public class SparseMatrix<anyType> implements Matrixable<anyType>
{
   private HashMap<Integer,anyType> list = new HashMap<Integer,anyType>();
   private int colSize; 
   private int rowSize;
   private int size;
   public SparseMatrix(int r, int c){
      colSize = r;
      rowSize = c;
   }
/**
 * returns the element at row r, col c. 
 *
 * @param  r    the row location; r >=0 and r is less than the number of rows.
 * @param  c    the column location; c >=0 and c is less than the number of columns.
 * @return the element at row r, column c.
 */   
   public int getKey(int r, int c){
      return r*colSize + c;
   }
   public anyType get(int r, int c){
            return list.get(getKey(r,c));
   }
      
      				

 /**
 * changes the element at row r, col c to a new value, returning the old value that was there. 
 *
 * @param  r    the row location; r >=0 and r is less than the number of rows.
 * @param  c    the column location; c >=0 and c is less than the number of columns.
 * @param  x    a non-null anyType object
 * @return the element that was at row r, column c before it was changed to x.
 */ 
   public anyType set(int r, int c, anyType x){
      anyType former = get(r,c);
      add(r,c,x);
      return former;
   }
      
      
 /**
 * adds a new element at row r, col c. 
 *
 * @param  r    the row location; r >=0 and r is less than the number of rows.
 * @param  c    the column location; c >=0 and c is less than the number of columns.
 * @param  x    a non-null anyType object
 */      
   public void add(int r, int c, anyType x){
      list.put(getKey(r,c), x);
      size++;
   }
  
 /**
 * removes and returns the element at row r, col c. 
 *
 * @param  r    the row location; r >=0 and r is less than the number of rows.
 * @param  c    the column location; c >=0 and c is less than the number of columns.
 * @return the element that was at row r, column c before it was removed.
 */       
   public anyType remove(int r, int c){
      if(list.get(getKey(r,c))==null)
      {
      size--;
      return list.get(getKey(r,c));
      }
      return null;
   }

 /**
 * returns the number of logical elements added to the matrix. 
 *
 * @return the number of logical elements added to the matrix.
 */    
   public int size(){			//returns # actual elements stored
      return size;
   }
 /**
 * returns the number of rows in the matrix. 
 *
 * @return the number of rows in the matrix.
 */      
   public int numRows(){		//returns # rows set in constructor
      return rowSize;
   }
 /**
 * returns the number of columns in the matrix. 
 *
 * @return the number of columns in the matrix.
 */      
   public int numColumns(){	//returns # cols set in constructor
      return colSize;
   }
   public String toString(){
      String ans = "";
      for(int i = 0; i<rowSize; i++){
         ans = ans + "\n";
         for(int j = 0; j<colSize; j++){
            if(list.keySet().contains(getKey(i,j))){
               ans = ans +"x"/*+list.get(getKey(i,j))*/;
            }
            else{
               ans = ans + "-";
            }
         }
      }
      return ans;
   }
   	/*  Extension methods:
      public boolean contains(anyType x);		//true if x exists in list
      public int[] getLocation(anyType x);	//returns location [r,c] of where x exists in list, null otherwise
      public Object[][] toArray();				//returns equivalent structure in 2-D array form
      public boolean isEmpty();					//returns true if there are no actual elements stored
      public void clear();							//clears all elements out of the list
		
		public void setBlank(char blank);		//allows the client to set the character that a blank spot in the array is
															//represented by in String toString()
   	*/



}