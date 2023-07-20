// Lab35st.java
// This is the student version of the Lab 35 assignment.


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
      
public class Lab2
{
	public static void main(String args[]) throws IOException
	{
		Scanner input = new Scanner(System.in); 
		System.out.println("LAB35 \n");
		ArrayList studentArray = new ArrayList(); 
		getList(studentArray); 
		displayArray(studentArray); 
		pause(); 
		NameTree tree1 = new NameTree(studentArray); 
		tree1.showTree(); 
		pause(); 
		input = new Scanner(System.in); 
		String name; 
		for( int i = 1; i <=5; i++) 
		{ 
			System.out.println("What person do you wish to delete? "); 
			name = input.nextLine(); 
			tree1.delete(name); 
		} 
		tree1.showTree(); 
		pause(); 
		System.out.println("What person do you wish to find? "); 
		name = input.nextLine(); 
		tree1.find(name);
		tree1.goodDeansList(); // prints all students with GPA >= 3.25 
		tree1.badDeansList(); // prints all students with GPA < 2.0
		System.out.println(); 
		System.out.println("The average GPA is  " + tree1.avgGPA()); 
		System.out.println();
	}


	

	
	public static void getList(ArrayList students) throws IOException
	{
		System.out.println("\nRetrieving Students.dat\n");
		FileReader inFile = new FileReader("Students.dat");	
		BufferedReader inStream = new BufferedReader(inFile);	     
		String s1,s2,s3;

		while( ((s1 = inStream.readLine()) != null) && 
			((s2 = inStream.readLine()) != null) && ((s3 = inStream.readLine()) != null) )	
		{
			String name = s1;
			int age = Integer.parseInt(s2);
			double gpa = Double.parseDouble(s3);
			students.add(new Person(name,age,gpa));       
		}   
		inStream.close();   					
	}
	
	
	public static void displayArray(ArrayList students)
	{
		System.out.println("\nDISPLAYING STUDENT ARRAY ELEMENTS");
		for (int k = 0; k < students.size(); k++)
		{
			Person person = (Person) students.get(k);
			System.out.println(person.name + "\t\t" + person.age + "\t\t" + person.gpa);
		}
	} 
	

// the following method is only for the NameTree
// you will have to adjust this for the 100 pt. version
	public static void displayTree(NameTree studentTree)
	{
   		studentTree.showTree();
	}  



	public static void pause() throws IOException
	{   
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));	 
		String dummy;
		System.out.print("\nPress <Enter> to continue  ===>>  ");						
		dummy = input.readLine();								
	}

}




class Person
{
	public String name;
	public int age;
	public double gpa;
   
	Person(String n, int a,double g)
	{
		name = n;
		age = a;
		gpa = g;
	}
	
	public String getName() { return name; }
	public int getAge()		{ return age;  }
	public double getGpa()	{ return gpa;  }
	
	public String toString()
	{
		String output="";
		output+= name+ "\t \t" + age + "\t \t" + gpa;
		return output;
	}
}




class TreeNode
{
	private Object value;
	private TreeNode left;
	private TreeNode right;
	
	public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
	{
	   value = initValue;
	   left = initLeft;
	   right = initRight;
	}
	
	public Object getValue()					{ return value; 		}
	public TreeNode getLeft()					{ return left; 			}
	public TreeNode getRight()					{ return right; 		}
	public void setValue(Object theNewValue)	{ value = theNewValue; 	}
	public void setLeft(TreeNode theNewLeft)	{ left = theNewLeft; 	}
	public void setRight(TreeNode theNewRight)	{ right = theNewRight; 	}
	
}




class NameTree
{
	private boolean first;
	private TreeNode root;
	
	public NameTree(ArrayList students)
	{
		first = true;
		root = null;
		for(int x = 0;x<students.size();x++)
		{
			add(students.get(x));
		}
	}
	
	public void add(Object value)
	{
		TreeNode t1 = null, t2 = null, t3 = null;
		if(first)
		{
			root = new TreeNode(value,null,null);
			first = false;
		}
		else
		{
			t1 = new TreeNode(value,null,null);
			t2=t3=root;
			Person p1 = (Person)t1.getValue();
			while(t2!=null)
			{
				t3=t2;
				Person p2 = (Person)t2.getValue();
				if(p1.getName().compareTo(p2.getName())>0)
					t2=t2.getRight();
				else
					t2=t2.getLeft();
			}
			Person p3 = (Person)t3.getValue();
			if(p1.getName().compareTo(p3.getName())>0)
				t3.setRight(t1);
			else
				t3.setLeft(t1);
		}
	}
	
	public void delete(Object value)
	{
		if(first)
		{
			root = new TreeNode(value,null,null);
			first = false;
		}
		else
		{
			TreeNode p = root;
			TreeNode temp = root;
			Person one = (Person)p.getValue();
			while(p!=null && one.getName()!=(String)value)
			{
				temp=p;
				Person x = (Person)p.getValue();
				String two = (String)value;
				if(x.getName().compareTo(two)>0)
					p=p.getLeft();
				else
					p=p.getRight();
			}
			if(p!=null && one.getName()==(String)value)
			{
				if(p.getLeft()==null&&p.getRight()==null)
					deleteLeaf(p,temp);
				else if(p.getLeft()==null || p.getRight()==null)
					deleteParent1(p,temp);
				else
					deleteParent2(p,temp);
			}
		}
	}
	
	public void deleteLeaf(TreeNode p, TreeNode temp)
	{
		if(p==temp)
			root=null;
		else
		{
			if(temp.getLeft()==p)
				temp.setLeft(null);
			else
				temp.setRight(null);
		}
	}
	
	public void deleteParent1(TreeNode p, TreeNode temp)
	{
		if(p==temp)
		{
			if(p.getLeft()==null)
				root=root.getRight();
			else
				root=root.getLeft();
		}
		else
		{
			if(temp.getLeft()==p)
				if(p.getLeft()==null)
					temp.setLeft(p.getRight());
				else
					temp.setLeft(p.getLeft());
			else
				if(p.getLeft()==null)
					temp.setRight(p.getRight());
				else
					temp.setRight(p.getLeft());
		}
	}
	
	public void deleteParent2(TreeNode p, TreeNode temp)
	{
		TreeNode temp1 = p.getLeft();
		TreeNode temp2 = p;
		while(temp1.getRight()!=null)
		{
			temp2=temp1;
			temp1=temp1.getRight();
		}
		p.setValue(temp1.getValue());
		if(p==temp2)
			temp2.setLeft(temp1.getLeft());
		else
			temp2.setRight(temp1.getLeft());
	}
	
	public void find(Object value)
	{
		Queue temp = new LinkedList();
		TreeNode p = root;
		if(p==null)
			System.out.println("The tree is empty");
		else
		{
			temp.add(p);
			while(!temp.isEmpty())
			{
				Person one = (Person)p.getValue();
				if(one.getName().equals(value))
					System.out.println("Person found: " + p.getValue());
				
				
				p=(TreeNode)temp.remove();
				if(p.getLeft()!=null)
					temp.add(p.getLeft());
				if(p.getRight()!=null)
					temp.add(p.getRight());
			}
		}
	}
	
	public void goodDeansList()
	{
		Queue temp = new LinkedList();
		TreeNode p = root;
		if(p==null)
			System.out.println("The tree is empty");
		else
		{
			temp.add(p);
			while(!temp.isEmpty())
			{
				Person x = (Person)p.getValue();
				if(x.getGpa()>=3.25)
					System.out.println("Good Deans List: " + p.getValue());
				p=(TreeNode)temp.remove();
				if(p.getLeft()!=null)
					temp.add(p.getLeft());
				if(p.getRight()!=null)
					temp.add(p.getRight());
			}
		}
	}
	
	public void badDeansList()
	{
		Queue temp = new LinkedList();
		TreeNode p = root;
		if(p==null)
			System.out.println("The tree is empty");
		else
		{
			temp.add(p);
			while(!temp.isEmpty())
			{
				Person x = (Person)p.getValue();
				if(x.getGpa()<2.0)
					System.out.println("Bad Deans List: " + p.getValue());
				p=(TreeNode)temp.remove();
				if(p.getLeft()!=null)
					temp.add(p.getLeft());
				if(p.getRight()!=null)
					temp.add(p.getRight());
			}
		}
	}
	
	public double avgGPA()
	{
		int count =1;
		double sum = 0;
		Queue temp = new LinkedList();
		TreeNode p = root;
		if(p==null)
			System.out.println("The tree is empty");
		else
		{
			temp.add(p);
			while(!temp.isEmpty())
			{
				Person x = (Person)p.getValue();
				sum+=x.getGpa();
				p=(TreeNode)temp.remove();
				if(p.getLeft()!=null)
					temp.add(p.getLeft());
				if(p.getRight()!=null)
					temp.add(p.getRight());
				count++;
			}
		}
		double avg = sum/count;
		return avg;
	}
	
	public void showTree()
	{
		System.out.println("\nStudent Tree Ordered By Name");
		traverseInOrder(root);
	}
	
	private void traverseInOrder(TreeNode p)
	{
		if(p!=null)
		{
			traverseInOrder(p.getLeft());
			System.out.println(p.getValue());
			traverseInOrder(p.getRight());
		}
	}
}	

class AgeTree
{
	private boolean first;
	private TreeNode root;
	
	public AgeTree(ArrayList students)
	{
		first = true;
		root = null;
		for(int x = 0;x<students.size();x++)
		{
			add(students.get(x));
		}
	}
	
	public void add(Object value)
	{
		TreeNode t1 = null, t2 = null, t3 = null;
		if(first)
		{
			root = new TreeNode(value,null,null);
			first = false;
		}
		else
		{
			t1 = new TreeNode(value,null,null);
			t2=t3=root;
			Person p1 = (Person)t1.getValue();
			while(t2!=null)
			{
				t3=t2;
				Person p2 = (Person)t2.getValue();
				if(p1.getAge()>p2.getAge())
					t2=t2.getRight();
				else
					t2=t2.getLeft();
			}
			Person p3 = (Person)t3.getValue();
			if(p1.getAge()>p3.getAge())
				t3.setRight(t1);
			else
				t3.setLeft(t1);
		}
	}
	public void showTree()
	{
		System.out.println("\nStudent Tree Ordered By Age");
		traverseInOrder(root);
	}
	private void traverseInOrder(TreeNode p)
	{
		if(p!=null)
		{
			traverseInOrder(p.getLeft());
			System.out.println(p.getValue());
			traverseInOrder(p.getRight());
		}
	}
}

class GPATree
{
	private boolean first;
	private TreeNode root;
	
	public GPATree(ArrayList students)
	{
		first = true;
		root = null;
		for(int x = 0;x<students.size();x++)
		{
			add(students.get(x));
		}
	}
	
	public void add(Object value)
	{
		TreeNode t1 = null, t2 = null, t3 = null;
		if(first)
		{
			root = new TreeNode(value,null,null);
			first = false;
		}
		else
		{
			t1 = new TreeNode(value,null,null);
			t2=t3=root;
			Person p1 = (Person)t1.getValue();
			while(t2!=null)
			{
				t3=t2;
				Person p2 = (Person)t2.getValue();
				if(p1.getGpa()>p2.getGpa())
					t2=t2.getRight();
				else
					t2=t2.getLeft();
			}
			Person p3 = (Person)t3.getValue();
			if(p1.getGpa()>p3.getGpa())
				t3.setRight(t1);
			else
				t3.setLeft(t1);
		}
	}
	public void showTree()
	{
		System.out.println("\nStudent Tree Ordered By GPA");
		traverseInOrder(root);
	}
	private void traverseInOrder(TreeNode p)
	{
		if(p!=null)
		{
			traverseInOrder(p.getLeft());
			System.out.println(p.getValue());
			traverseInOrder(p.getRight());
		}
	}
}		