<h1>Introduction</h1>

INTUZ is presenting Permission Manager component, which lets you manage runtime permission to give support for marshmallow and above API. Please follow below steps to integrate this control in your project.

<br>
<h1>Features</h1>

- An easy & fast ways to manage permission.
- It allows you to check for permission, if it is granted or not.
- It will allow you to manage all permission and notify you without managing for its result on your own.


<br>
<img src="Screenshots/permission_manager.gif" width=500 alt="Screenshots/permission_manager.png">

<h1>Getting Started</h1>

> Add all permissions that you want to request in AndroidManifest.xml file

```
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
> Import permission manager module in your project


> Create instance of PermissionManagerInstance class in your activity

```
   private PermissionManagerInstance mPermissionManagerInstance = new PermissionManagerInstance(MainActivity.this);

```

> Set Value for Dialog if you want to show dialog for already denied permissions.

```
    mPermissionManagerInstance.setShowMessageOnRationale(true);
    mPermissionManagerInstance.setShowMessageBeforeRequest(false);
    mPermissionManagerInstance.setTitle("We require all permissions....");
    mPermissionManagerInstance.setMessage("We require all permissions....");
    mPermissionManagerInstance.setNegativeButtonText("Deny");
    mPermissionManagerInstance.setPositiveButtonText("Allow");


```

> Check for Permission

```
    if (mPermissionManagerInstance.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                //permission granted
            } else
            {
             //permission is not granted
            }
```

> Request Permission

```
     String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, 
                                         Manifest.permission.READ_CONTACTS, 
                                         Manifest.permission.ACCESS_FINE_LOCATION};


    mPermissionManagerInstance.requestForPermissions(permissions, new 	PermissionManagerListener() {
	    @Override
	    public void permissionCallback(String[] permissions, Permission[] grantResults,boolean allGranted) {
	        //handle your result
	    }
	});

```


<h1>Bugs and Feedback</h1>

For bugs, questions and discussions please use the Github Issues.

<br>
<h1>License</h1>

Copyright (c) 2018 Intuz Solutions Pvt Ltd.
<br><br>
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
<br><br>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

<h1></h1>
<a href="http://www.intuz.com">
<img src="Screenshots/logo.jpg">
</a>