Main Activity
in main activity i have implemented 5 methods
    1 validate email
    2 validate phone 
    3 storedata
    4 loadstatecitydetails
    5 fetch data

  Validate Email:
           for validating email address i have write a one expression . 
  Validate Phone:
            in this method for validating phone number i have used one expression.
  store data:
            After validating email and phone number contorl is goes to storedata method in this data is store in firebase.
            

  loadstatecitydetails
             for fetching state and city details i have used third party library 'com.android.volley:volley:1.0.0' , three java class
             i.e MYSINGELTON, STATEADAPTER,STATE and two xml file city_list.xml, state_list.xml
   fetch data 
            This is a button which switch the activity from main activity to Content Activity .

    Content Activity     
        for displaying i have implemented 3 folder:- 
            1 Interface 
            2 Model 
            3 ViewHolder
            Model:
                in a model folder i have implemented user class. in this i have implemented getter and setter methods for getting data.
             For recycler view i have implemented user_details.xml file activity_content.xml is the container of user_details.xml file
             
 How to use this app?
    1 enter your unique mobile number.
    2 enter your email id.
    3 select your state name.
    4 select your city name.
    5 Press fetch button  to check your details  
                
                                                      
