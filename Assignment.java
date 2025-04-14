/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment;

/**
 *
 * @author mokwena
 */
import javax.swing.JOptionPane;

public class Assignment {
        
static boolean ValidateUsername(String UserNameVal)
{
  int Underscore = UserNameVal.indexOf('_');
        int CharLength = UserNameVal.length();
        boolean ReturnVal;
        if (Underscore == -1 || CharLength>5)
        {
          ReturnVal=false;  
         //
        }
        else
        {
            ReturnVal=true;
           // 
        }
        return(ReturnVal);
}
static boolean ValidatePassword(String PasswordVal)
{

    if(PasswordVal.length()<8)
   {
       return(false);
   }
   boolean isUpper=false;
   boolean hasNumber=false;
   boolean hasSpecial=false;
   for(int i=0;i<PasswordVal.length();i++)
   {
       char EachChar= PasswordVal.charAt(i);
       if(Character.isUpperCase(EachChar))
       {
           isUpper=true;
       }
       if(Character.isDigit(EachChar))
       {
           hasNumber=true;
       }
       if(!Character.isLetter(EachChar)&& !Character.isLetter(EachChar)&&!Character.isWhitespace(EachChar))
       {
           hasSpecial=true;
       }
       
   }
   return(isUpper&&hasNumber&&hasSpecial);
}
static boolean ValidateCellNo(String CellNoVal)
{
    int PlusInt = CellNoVal.indexOf('+');
 if (CellNoVal.length()>12||PlusInt==0)
    {//+27795082619
        return true;
    }
 
 else 
    {
        return false;   
    }
}
static boolean LoginCheck(String LogName,String LogPassword,String PasswordVal,String UserNameval)
{
    if (LogName==UserNameval&&LogPassword==PasswordVal)
    {
    return true;    
    }
    else 
    {
        return false;
    }
    
}
 
    


    public static void main(String[] args)
    {
        String Password="";
        String CellNo;
        String UserName;
        UserName = JOptionPane.showInputDialog("Please Create A username");
        //Question1
        if(ValidateUsername(UserName)==true)
        {    
        JOptionPane.showMessageDialog(null,"Username is succesfully captured","Danko",1);
        Password = JOptionPane.showInputDialog("Please Create a password for Username: "+UserName);  
            if(ValidatePassword(Password)==true)
            {
                
                JOptionPane.showMessageDialog(null,"Password is succesfully captured","Danko",1);
                CellNo = JOptionPane.showInputDialog("Please Add a cellphone number for Username: "+UserName);
                if(ValidateCellNo(CellNo)==true)
                {
                    JOptionPane.showMessageDialog(null,"Cellphone number is succesfully captured","Danko",1);
                    
                    
                }
                else if(ValidateCellNo(CellNo)==false)
                {
                    JOptionPane.showMessageDialog(null,"Cell phone number incorrectly formatted or does not contain international code.","Error",0);
                  
                    
                }
            }    
            else if(ValidatePassword(Password)== false)
            {
                JOptionPane.showMessageDialog(null,"Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.","Error",0);      
            }
        }
        else if(ValidateUsername(UserName)==false)
        {
            JOptionPane.showMessageDialog(null,"Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length","Error",0);  
        }
       
       //Question2
       String LoginName = JOptionPane.showInputDialog("Please Enter the same Username");
       String LoginPassword = JOptionPane.showInputDialog("Please Enter the same Password");
        if(LoginCheck(LoginName, LoginPassword, Password, UserName)==true)
        {
        
            JOptionPane.showMessageDialog(null,"Welcome"+UserName+", "+LoginName+" its great to see you again","Login Succesful",0);
        }
        else if(LoginCheck(LoginName, LoginPassword, Password, UserName)==false)
        {
         JOptionPane.showMessageDialog(null,"Username or password incorrect, please try again.","Error",0);   
        }
        
       
       
    }
}
