# PoC of CRM introduction

Backend:  
1.Libraries: Spring Boot,Spring Data Jpa,H2,Lombok,Sping-Test,Mokito,Swagger  
2.Code layers: repository,service,controller 
3.UT:Junit,Mokito
3.DB: H2(in memory DB)  
4.Build tool:Maven  
5.Runtime container:embeded tomcat.  

Frontend:  
1:libraries:vuejs,Element-ui,axios  
2.In this PoC,all codes in index.html for fast release.  

How to run:  
Fetch project from git repository, import to IDE(Eclipse.etc.).Maven build then trigger run in IDE or run jar.  

Rest APIs document:  
http://localhost:8080/swagger-ui.html  

Home page:  
http://localhost:8080/index.html  

Functionalities:  
Customer: 
1.create(click '+' to add new customer and click create to save)  
2.retrieve(press enter to do search)  
3.update(select line item in table customers,then change the data in area below,click update to save)   

Note:  
1.retrieve(click 'detail' button in customer line item,then all notes of this customer will be shown in popup dialog)  
2.create(in the popup dialog, click '+' to add new note,then click 'save' button to save)  
3.update(in popup dialog,click 'edit' button,then update note,click 'save' button to save )  
