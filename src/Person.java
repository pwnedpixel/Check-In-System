/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class Person
{

    public String firstName = "";
    public String lastName = "";
    public String id = "";
    public String DOB = "";
    public String address = "";

    //Constructors
    public Person(String firstNameIn, String lastNameIn, String idIn, String DOBin, String addressIn)
    {
        firstName = firstNameIn;
        lastName = lastNameIn;
        id = idIn;
        DOB = DOBin;
        address = addressIn;
    }

    public Person()
    {

    }

    //Functions
    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getID()
    {
        return id;
    }

    public String getDOB()
    {
        return DOB;
    }

    public String getAddress()
    {
        return address;
    }

}
