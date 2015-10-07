//package com.anupcowkur.mvpsample.model.data;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.app.Fragment;
//
//import com.anupcowkur.mvpsample.events.ErrorEvent;
//import com.anupcowkur.mvpsample.events.NewPostsEvent;
//
//
//import java.util.List;
//
//import de.greenrobot.event.EventBus;
//import retrofit.http.GET;
//import rx.Observable;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by Raghunandan on 30-09-2015.
// */
//public class FetchPostsFragment extends Fragment {
//
//    private Observable<List<Post>> postsObservable;
//    private List<Post> posts;
//
//    public void setPosts(List<Post> posts) {
//        this.posts = posts;
//    }
//
//    public List<Post> getPosts() {
//        return posts;
//    }
//
//    public interface TaskCallbacks {
//        void onPreExecute();
//        void onProgressUpdate(int percent);
//        void onCancelled();
//        void onPostExecute();
//    }
//
//    private interface PostService {
//        @GET("/posts")
//        Observable<List<Post>> getPostsList();
//    }
//
//
//    private TaskCallbacks mTaskCallbacks;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//         postsObservable = new RestAdapter.Builder()
//                .setEndpoint("http://jsonplaceholder.typicode.com")
//                .build().create(PostService.class).getPostsList().cache();
//
//        postsObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
//                .mainThread())
//                .subscribe(new Subscriber<List<Post>>() {
//                    @Override
//                    public void onNext(List<Post> newPosts) {
//
//                        setPosts(newPosts);
//                        EventBus.getDefault().post(new NewPostsEvent(newPosts));
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        EventBus.getDefault().post(new ErrorEvent());
//                    }
//
//                });
//    }
//
//
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //postsObservable.unsubscribeOn(Schedulers.io());
//    }
//
//    public Observable<List<Post>> getPostsObservable() {
//        return postsObservable;
//
//    }
//
//}
