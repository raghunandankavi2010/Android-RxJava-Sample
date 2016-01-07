# MVP with RxJava, DAGGER 2, and RETROFIT
A sample project showing the use of MVP in android. This is forked repository and modified for the sake of learning. 

Part of learning process of implementing MVP design pattern in android. Thanks to the Author (Anup Cowkur).

Presented as part of talk by Anup Cowkur at Bangalore Android Users Group: [Wrting Better Android Apps with MVP](http://slides.com/anupcowkur/writing-better-android-apps-with-mvp).

Note :

There is a load more button to fetch the same feed for testing purpose( To test load more). 

Ideally you will have a recyclerview scroll listener for pagination. 

This app is for testing purpose only.

Explanation :

We use dagger 2, retrofit and Rx java. Presenter is a singeton. Using Rx Java we can cache the observable. So during configuration change, you are required to subscibe to the same observable rather than creating a new one.

We obsever on the main thread. We can use EventBus or RxJava for event bus. If you don't want another library to be added use RxJava as a EventBus. (http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/)

We can also invalidate the cache once we update the ui. During configuration change if you have data you can set the same in presenter and get the data using gettter.

Since presenter is a singleton and you only inject the same in the activity , you can set and retrieve data easily.