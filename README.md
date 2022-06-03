# springboot-app-sample


## about
a demo for using  springboot


## Technologies useed

* java 17
* springboot
* spring Security
* spring data
* Thymeleaf
* lombok
* Liquibase
* Java Mail Sender

## features 
* login page 
* sign up page
* validation form 
* activated email link
* logout
* show all Staff 
* using pagination
* show all Department 
* create new staff
* delete staff
* create new department 
        



##  the home page:
* ( http://127.0.0.1:8080/home ) you need to authenticate before accessing this page 
* to login as admin use  username: **admin** , password: **admin** 
* to login as user use  username: **user** , password: **user** 
### for rest api 
#### there are two resourses **Staff** and **Department**
* use this ( http://127.0.0.1:8080/api/staff ) support all method requests [get,post,put,delete], you need to add basic auth to perform any request. use the credentials provided above.
* use this ( http://127.0.0.1:8080/api/department ) support all method requests [get,post,put,delete], you need to add basic auth to perform any request. use the credentials provided above.
## for email 
#### The following environment variables are necessary:
      EMAIL_USER_NAME : your gmail 
      EMAIL_PASSWORD : the App password , you can gen generate the app password from your account managment


