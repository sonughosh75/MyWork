
I. Summary of the Application.

The application has Rest clients created that basically does the following:-

1. Add Category
2. Get All valid categories with count and also the categories with the duplicates removed.
3. Remove an existing ctegory

The Rest API endpoints are as follows:-

1. Add category - http://localhost:8081/data-web/doj/category/addCategory
2. Get Categories - http://localhost:8081/data-web/doj/category/categoryData
3. Remove Category - http://localhost:8081/data-web/doj/category/removeCategory

For the purpose of the assignment, the Animal catregory is added and then removed.


II. Testing the Application

1. From Tomcat server (from the data web project), run the server and hit the following URL 
   http://localhost:8081/data-web/doj/category/categoryData
2. From the Rest client - Right on the RestClient (org.dataprocess.rest.client.RestClient) and the console will display the required output.

   