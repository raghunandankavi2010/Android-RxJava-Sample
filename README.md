# MVP with RxJava, DAGGER 2, and RETROFIT
A sample project showing the use of MVP in android based on what i learnt at a android meetup.

Note :

There is a load more button to fetch the same feed for testing purpose.( To test load more). 

Ideally you will have a recyclerview scroll listener for pagination. 

This app is for testing purpose only.

Explanation :

Model - does the part of fetching data and communicates the same to presenter/

Presenter - is the middleman communicating with both Model and Presenter/

View - Just updates the ui.

You make a network request on a different thread and subscribe to it on the main thread with the help of Schedulers.

PostsApi is a singleton. You make network request and fetch the data. Now if there is a configuration change and your request is made and response yet to be recieved, you don't wan to start a new Network operation.

This problem can be taken care of by RxJava operator .cache(). All you need to do is subscribe to the observable and .cache() contines request. You will have the emitted items once.

There is blog post by Dan Lew explaining the same @ http://blog.danlew.net/2014/10/08/grokking-rxjava-part-4/
