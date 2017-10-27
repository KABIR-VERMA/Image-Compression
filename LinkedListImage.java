//import .LinkedListImage;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;



// NODE
class Node{
    int data;
    Node next;
    Node(int data){
        this.data=data;
        next=null;
    }
}

//linkeList class 
class LinkedList{
    Node head;
    
    //insertion in linked list
    void insert(int data){
        Node n=new Node(data);
        if(head==null){
            head=n;
        }
        else{
            Node tmp=head;
            while(tmp.next!=null){
                tmp=tmp.next;
            }
            tmp.next=n;
        }
    }

    
    public int getCountRec(Node node)
    {
       
        if (node == null)
            return 0;
 
        
        return 1 + getCountRec(node.next);
    }
 
    
    public int getCount()
    {
        return getCountRec(head);
    }


    
    public int GetNth(int index)
    {
        Node current = head;
        int count = 0; 
        while (current != null)
        {
            if (count == index)
                return current.data;
            count++;
            current = current.next;
        }
        
        
        assert(false);
        return 0;
    }


    //push node of value item at given pos
    public void pushnth(int item, int pos){
        Node temp = head;
        if(pos==0){
            Node n=new Node(item);
            n.next=head;
            head=n;
            return;            
        }
        for(int i=0;i<pos-1;i++){
            temp=temp.next;
        }
        Node n=new Node(item);
        n.next=temp.next;
        temp.next=n;
        
    }
    
    
    //delete node at given position 
    public void deleteNode(int position)
    {
        
        if (head == null)
            return;
 
        
        Node temp = head;
 
        
        if (position == 0)
        {
            head = temp.next;   
            return;
        }
 
        
        for (int i=0; temp!=null && i<position-1; i++) 
            temp = temp.next;
 
       
        if (temp == null || temp.next == null)
            return;
 
        
        Node next = temp.next.next;
 
        temp.next = next;  
    }
   
}

public class LinkedListImage implements CompressedImageInterface {
    LinkedList l[]; 
    
    //1 correct
    public LinkedListImage(String filename) throws FileNotFoundException
	{
        //you need to implement this
        FileReader sampleInputFile = new FileReader(filename);
        
        Scanner sample = new Scanner(sampleInputFile);
        int i = sample.nextInt();
        int j = sample.nextInt();
        l=new LinkedList[i];
        for(int k=0;k<i;k++){
            l[k]=new LinkedList();
        }
        for(int k=0;k<j;k++){
                int c=0;
            for(int m=0;m<j;m++){
                int s=sample.nextInt();
                if(s==0){
                    c++;
                }
                else if(s==1 && c!=0){
                    l[k].insert(m-c);//k==>s
                    l[k].insert(m-1);//k==>s
                    c=0;
                }
                if(s==0&&m==j-1){
                
                    l[k].insert(j-c);
                    l[k].insert(j-1);
                    c=0;
            }
        }
    }sample.close();
    }
    //2 correct
   
    public LinkedListImage(boolean[][] grid, int width, int height)
    {
        //you need to implement this
        l = new LinkedList[grid.length];
        for(int k =0;k<grid.length;k++){
            l[k]=new LinkedList();
        }
        for(int k=0; k<grid.length;k++){
            int c =0;
            for(int m=0;m<grid[0].length;m++){
                //int c =0;
                if(grid[k][m]==false){
                    c++;
                }
                else if(grid[k][m]==true && c!=0 ){
                    l[k].insert(m-c);//k==>s
                    l[k].insert(m-1);//k==>s
                    c=0;
                }
                if(grid[k][m]==false&&m==grid[0].length-1){
                
                    l[k].insert(grid[0].length-c);
                    l[k].insert(grid[0].length-1);
                    c=0;
            }
        }
    }
    }

        



		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    

    //3 correct
    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
        //you need to implement this
     if(x>l.length||x<0 || y<0|| y>l.length){
            throw new PixelOutOfBoundException("ERROR 404");
        }
        boolean aaa=true;
        if(l[x].getCount()==0){
            
            return aaa;
        }
        for(int m=0;m<l[x].getCount();m++){
            
            if(y<=l[x].GetNth(m)){
                if(y==l[x].GetNth(m)){
                    aaa=false;
                    break;
                }else{
                    if(m%2==0){
                        aaa=true;
                        break;
                    }else{
                        aaa=false;
                        break;
                    }
                }

                
                
            }
        
        
        }return aaa;
    }


    //4 correct
    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
        /*you need to implement this*/
        if(x>l.length||y>l.length){
            throw new PixelOutOfBoundException("Error 404");
        }
        if(getPixelValue(x, y) == false){
            if(val == true){
                for(int j=0;j<l[x].getCount();j++){
                    if(y==l[x].GetNth(j) && j%2==0){
                        if(l[x].GetNth(j)==l[x].GetNth(j+1)){
                            l[x].deleteNode(j);
                            l[x].deleteNode(j);
                            break;
                        }else{

                            l[x].pushnth(y+1, j);
                            l[x].deleteNode(j+1);
                            break;
                        }
                    }
                    else if(y==l[x].GetNth(j) && j%2==1){
                        l[x].pushnth(y-1, j);
                        l[x].deleteNode(j+1);
                        break;
                    }
                    else if(y<l[x].GetNth(j)){
                        l[x].pushnth(y+1, j);
                        l[x].pushnth(y-1, j);
                        break;
                    }
                }

                }
            }
        else{
            if(val== false){
                if(l[x].getCount()==0){
                        l[x].insert(y);
                        l[x].insert(y);
                        return;
                }

                else if(y<l[x].GetNth(0)){
                    if(l[x].GetNth(0)-y>1){
                        l[x].pushnth(y, 0);
                        l[x].pushnth(y, 0);
                    }else if(l[x].GetNth(0)-y==1){
                        l[x].deleteNode(0);
                        l[x].pushnth(y, 0);
                    }
                }
                else if(y>l[x].GetNth(l[x].getCount()-1)){
                    if(y-1==l[x].GetNth(l[x].getCount()-1)){
                        l[x].deleteNode(l[x].getCount()-1);
                        l[x].insert(y);
                        
                    }else if((y-l[x].GetNth(l[x].getCount()-1))>1){
                        l[x].insert(y);
                        l[x].insert(y);
                    }
                }
                else{
                for(int k=0; k<l[x].getCount();k++){
                    if(y<l[x].GetNth(k)){
                        
                        if(y-l[x].GetNth(k-1)>1 && l[x].GetNth(k)-y>1){
                                l[x].pushnth(y, k);
                                l[x].pushnth(y, k+1);
                                break;
                        }
                        else if((y-l[x].GetNth(k-1))==1 && (l[x].GetNth(k)-y)>1){
                                l[x].pushnth(y, k);
                                l[x].deleteNode(k-1);
                                break;
                        }else if((y-l[x].GetNth(k-1))>1 && (l[x].GetNth(k)-y)==1){
                                l[x].pushnth(y, k);
                                l[x].deleteNode(k+1);
                                break;
                        }else{
                                l[x].deleteNode(k);
                                l[x].deleteNode(k-1);
                                break;
                        }
                        }
                    }
                }
            
            }
        }




        
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }



    //5 correct
    public int[] numberOfBlackPixels()
    {
        //you need to implement this
        int[] aa =new int[l.length];

        
        for(int j=0; j<l.length;j++){
            int count =0;
            if(l[j].getCount()==0){
                aa[j]=0;
                
            }else{
                for(int k=1;k<l[j].getCount();k=k+2){
                     count= count+l[j].GetNth(k)-l[j].GetNth(k-1)+1;
                }
                aa[j]=count;
            }    
        }
        return aa;

		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    

    //6 correct
    public void invert()
    {
        //you need to implement this
        for(int j=0; j<l.length;j++){
            if(l[j].getCount()==0){
                l[j].insert(0);
                l[j].insert(l.length-1);
            }else if(l[j].getCount()==l.length){
                l[j].deleteNode(0);
                l[j].deleteNode(0);
            }
            else if(l[j].GetNth(0)!=0 || l[j].GetNth(l[j].getCount())!=l.length){
                l[j].insert(0);
                while(l[j].GetNth(0)!=0){
                    l[j].insert(l[j].GetNth(0)-1);
                    l[j].deleteNode(0);
                    l[j].insert(l[j].GetNth(0)+1);
                    l[j].deleteNode(0);
                }
                l[j].insert(l.length-1);


            }else if(l[j].GetNth(0)==0 && l[j].GetNth(l[j].getCount())!=l.length){
                l[j].deleteNode(0);
                int h = l[j].GetNth(0)+1;
                l[j].insert(l[j].GetNth(0)+1);
                l[j].deleteNode(0);
                while(l[j].GetNth(0)!=h){
                    l[j].insert(l[j].GetNth(0)-1);
                    l[j].deleteNode(0);
                    l[j].insert(l[j].GetNth(0)+1);
                    l[j].deleteNode(0);
                }
              
                
            }

            else if(l[j].GetNth(0)!=0 || l[j].GetNth(l[j].getCount())==l.length){
                l[j].insert(0);
                while(l[j].GetNth(0)!=0){
                    l[j].insert(l[j].GetNth(0)-1);
                    l[j].deleteNode(0);
                    if(l[j].GetNth(0)==(l.length-1)){
                        l[j].deleteNode(0);
                        break;
                    }
                    l[j].insert(l[j].GetNth(0)+1);
                    l[j].deleteNode(0);
                }
                 
            }
            else{
                l[j].deleteNode(0);
                int h = l[j].GetNth(0)+1;
                l[j].insert(l[j].GetNth(0)+1);
                l[j].deleteNode(0);
                while(l[j].GetNth(0)!=h){
                    l[j].insert(l[j].GetNth(0)-1);
                    l[j].deleteNode(0);
                    if(l[j].GetNth(0)==(l.length-1)){
                        l[j].deleteNode(0);
                        break;
                    }
                    l[j].insert(l[j].GetNth(0)+1);
                    l[j].deleteNode(0);
                }
                          
                          
             
            }
              
         }
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        
    }
    

    //7
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
        //you need to implement this
        LinkedListImage imm = (LinkedListImage)img;
        int himm = imm.l.length;
        int h = this.l.length;
        if(himm!=h){
            throw new BoundsMismatchException("Error 404");
        }else{
        //LinkedList temp = new LinkedList();
        for(int j=0;j<h;j++){
            for(int i=0;i<h;i++){
                try{
                    if(this.getPixelValue(j, i)==true && imm.getPixelValue(j,i)==true ){
                        this.setPixelValue(j, i, true);
                    }
                    else{
                        this.setPixelValue(j, i, false);
                    }
                }
                catch(PixelOutOfBoundException e){}

            }
        }
    }


        
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    

    //8
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
        //you need to implement this
        LinkedListImage imm = (LinkedListImage)img;
        int himm = imm.l.length;
        int h = this.l.length;
        if(himm!=h){
            throw new BoundsMismatchException("Error 404");
        }else{
        //LinkedList temp = new LinkedList();
        for(int j=0;j<h;j++){
            for(int i=0;i<h;i++){
                try{
                if(this.getPixelValue(j, i)==false && imm.getPixelValue(j,i)==false ){
                    this.setPixelValue(j, i, false);
                }else{
                    this.setPixelValue(j, i, true);
                }
            }
            catch(PixelOutOfBoundException e){}
        }
    }
    }   
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    

    //9
    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
        //you need to implement this
        LinkedListImage imm = (LinkedListImage)img;
        int himm = imm.l.length;
        int h = this.l.length;
        if(himm!=h){
            throw new BoundsMismatchException("Error 404");
        }else{
        //LinkedList temp = new LinkedList();
        for(int j=0;j<h;j++){
            for(int i=0;i<h;i++){
                try{
                if(this.getPixelValue(j, i)== imm.getPixelValue(j,i) ){
                    this.setPixelValue(j, i, false);
                }else{
                    this.setPixelValue(j, i, true);
                }
            }
            catch(PixelOutOfBoundException e){}
        }
    }
    }
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    

    //10 correct
    public String toStringUnCompressed()
    {
        //you need to implement this
        String s ="";
        s=s+l.length+" "+l.length+",";
        for(int j=0;j<l.length;j++){
            int h =0;
            int x =1;
            if(l[j].getCount()==0){
                for(int i=0; i<l.length;i++){
                    s=s+"1 ";
                }s=s.substring(0,s.length()-1)+",";
            }else if(l[j].getCount()==l.length){
                for(int i=0;i<l.length;i++){
                    s=s+"0 ";
                }s=s+", ";
            }
            
            else{
            for(int k=0;k<l.length;k++){
                if(l[j].GetNth(h)==l[j].GetNth(h+1)){
                    h++;
                }
                if(k!=l[j].GetNth(h)){
                    s=s+" "+x;
                }else{
                    if(h%2==0){
                        s=s+" "+0;
                        h++;
                        x=0;
                    }else{
                        s=s+" "+0;
                        h++;
                        x=1;
                    }

                }
            }
            s=s+",";
        }

        }s = s.substring(0,s.length()-1);
        return s;
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
    

    //11 correct
    public String toStringCompressed()
    {
        //you need to implement this
        String s="";
        s=s+""+l.length+" "+l.length+", ";
        for(int j=0;j<l.length;j++){
            if(l[j].getCount()==0){
                s=s+"-1, ";
    
            }else{
            for(int k=0;k<l[j].getCount();k++){
                s=s+l[j].GetNth(k)+" ";

            }s=s+"-1, ";
        }
        }s = s.substring(0,s.length()-2);
        return s;
		//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    //------------------------
    public void print(){
        for(int i=0;i<l.length;i++){
            Node t=l[i].head;
            while(t!=null){
                System.out.print(t.data+" ");
                t=t.next;
            }
            System.out.println();
        }
    }

    //-------------------------

    
    public static void main(String[] args) throws FileNotFoundException{
    	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));

    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));

    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
        }
        
        
    	// check Xor
        try
        {
            img1.performXor(img2); 
            
              
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        
        
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
                    
                    success = success && (!img1.getPixelValue(i,j));  
                                 
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}
        
    	// check setPixelValue
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        
    	// check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}
        
    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try
        {
            img1.performOr(img2);        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);    
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
       
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringCompressed().equals(img_ans));

        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}