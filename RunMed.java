/* Teeam -- Adam Mckoy, Ayman Ahmed, Derek Lin
   APCS2 pd5
   HW46 -- Running M[edi]an
   2016-05-27 */

/*****************************************************
 * class RunMed
 * Implements an online algorithm to track the median of a growing dataset
 * Maintains 2 heaps: minheap for upper half of dataset, maxheap for lower half
 * Median will always be one of these:
 *  - max of left heap  (lower range)
 *  - min of right heap (upper range)
 *  - mean of heap roots
 *****************************************************/

public class RunMed {

    //instance vars
    private ALMaxHeap leftHeap;  //for lower range of dataset
    private int leftHeapSize;
    private ALMinHeap rightHeap; //for upper range of dataset
    private int rightHeapSize;

    /*****************************************************
     * default constructor  ---  inits empty heap
     *****************************************************/
    public RunMed() 
    { 
        leftHeap = new ALMaxHeap();
	leftHeapSize = 0;
        rightHeap = new ALMinHeap();
	rightHeapSize = 0;
    }//O(1)

    /*****************************************************
     * double getMedian()  ---  returns median of dataset
     *****************************************************/
    public double getMedian() 
    {
	if( isEmpty() ){
	    System.out.println( "empty" );
	    return -1;
	}
	else if( rightHeapSize == leftHeapSize ){
	    return ( ( leftHeap.peekMax() + rightHeap.peekMin() ) / 2.0 );
	}
	else if( leftHeapSize == rightHeapSize + 1 ){
	    return leftHeap.peekMax();
	}
	else if( rightHeapSize == leftHeapSize + 1 ){
	    return rightHeap.peekMin();
	}
	else{
	    System.out.println( "Heaps are not correctly organized, this code should not even be reached." );
	    System.out.println( "leftHeap (maxHeap of lower half)" );
	    System.out.println( leftHeap );
	    System.out.println( "rightHeap (minHeap of upper half)" );
	    System.out.println( rightHeap );
	    return -1.0;
	}
    }//O(1)

    /*****************************************************
     * insert(int)  ---  adds a new element to the dataset
     * postcondition: dataset is maintained such that 
     *                getMedian() can run in constant time
     *****************************************************/
    public void insert( int addVal ){
	if( isEmpty() ){
	    leftHeap.add( addVal );
	    leftHeapSize++;
	}
	else if( leftHeapSize == 0 && rightHeapSize != 0 ){
	    if( addVal <= rightHeap.peekMin() ){
		leftHeap.add( addVal );
		leftHeapSize++;
	    }
	    else{
		leftHeap.add( rightHeap.removeMin() );
		leftHeapSize++;
		rightHeap.add( addVal );
	    }
	}
	else if( leftHeapSize != 0 && rightHeapSize == 0 ){
	    if( addVal >= leftHeap.peekMax() ){
		rightHeap.add( addVal );
		rightHeapSize++;
	    }
	    else{
		rightHeap.add( leftHeap.removeMax() );
		rightHeapSize++;
		leftHeap.add( addVal );
	    }
	}
	else if( addVal <= rightHeap.peekMin() ){
	    leftHeap.add( addVal );
	    leftHeapSize++;
	}
	else if( addVal >= leftHeap.peekMax() ){
	    rightHeap.add( addVal );
	    rightHeapSize++;
	}
	
        balance();
	/*
	  System.out.println( "leftHeap (maxHeap of lower half)" );
	  System.out.println( leftHeap );
	  System.out.println( "rightHeap (minHeap of upper half)" );
	  System.out.println( rightHeap );
	*/
    }//O(1)

    public void balance(){
	if( leftHeapSize > rightHeapSize + 1 ){
	    rightHeap.add( leftHeap.removeMax() );
	    rightHeapSize++;
	    leftHeapSize--;
	    balance();
	}
	else if( rightHeapSize > leftHeapSize + 1 ){
	    leftHeap.add( rightHeap.removeMin() );
	    leftHeapSize++;
	    rightHeapSize--;
	    balance();
	}
    }//O(1)

    /*****************************************************
     * boolean isEmpty()  ---  tells whether a median may be calculated
     * postcondition: dataset structure unchanged
     *****************************************************/
    public boolean isEmpty(){
        return leftHeap.isEmpty() && rightHeap.isEmpty();
    }//O(?)

    //main method for testing
    public static void main( String[] args ) {

        RunMed med = new RunMed();

        med.insert(1);
	System.out.println( "getMedian(): Expected: \"1.0\", Result: " + med.getMedian() + "." ); //1
        med.insert(3);
        System.out.println( "getMedian(): Expected: \"2.0\", Result: " + med.getMedian() + "." ); //2
        med.insert(5);
        System.out.println( "getMedian(): Expected: \"3.0\", Result: " + med.getMedian() + "." ); //3
        med.insert(7);
        System.out.println( "getMedian(): Expected: \"4.0\", Result: " + med.getMedian() + "." ); //4
        med.insert(9);
        System.out.println( "getMedian(): Expected: \"5.0\", Result: " + med.getMedian() + "." ); //5
	/*~~~~~V~~~~~~~~~~~~move~me~down~~~~~~~~~~~~~V~~~
	  ~~~~~|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|~~~*/

    }//end main()

}//end class RunMed
