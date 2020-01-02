# Study Manager App

The main purpose of this app is to keep track of your study time and show you statistics on how you spend your time





Developed by Helmi Dayyeh 

Copyright© 2020 All rights reserved.

#### Used technologies: 

- Kotlin Front-End
- ASP.NET Core Back-End
- Room Database
- Mockk Testing
- Material Dialogs provided by -> https://github.com/afollestad/material-dialogs
- JWT token Authentication

#### Default user provided by back-end

```C#
User admin = new User("user@testing.be");
await _userManager.CreateAsync(admin, "TestWachtwoord");
await _userManager.AddClaimAsync(admin, new Claim(ClaimTypes.Role, "User"));
_context.SaveChanges();
```

Email : user@testing.be

Password : TestWachtwoord

## Running the API

The app requires the back-end API to be started and kept running in the background.

This can be achieved by following the steps provided below.

##### 1. Open the Backend folder
![Annotation 2020-01-02 162500](https://user-images.githubusercontent.com/1749704/71675001-1550cc00-2d7d-11ea-99a4-b4054e50f7ee.png)

##### 2. Double click on StudyAPI.csproj
![Annotation 2020-01-02 162705](https://user-images.githubusercontent.com/1749704/71675031-33b6c780-2d7d-11ea-9b95-337b6ee55a3c.png)
##### 3. Run the Application
![Annotation 2020-01-02 162802](https://user-images.githubusercontent.com/1749704/71675045-3dd8c600-2d7d-11ea-86f9-cfd1234d23a7.png)



### When done correctly the API can be reached on localhost:5001

![image](https://user-images.githubusercontent.com/1749704/71675126-6791ed00-2d7d-11ea-8ff3-746020077a7f.png)





