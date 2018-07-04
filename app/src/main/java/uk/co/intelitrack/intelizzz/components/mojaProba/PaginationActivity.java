package uk.co.intelitrack.intelizzz.components.mojaProba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;

public class PaginationActivity extends AppCompatActivity {

    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    private ProgressBar progressBar;
    private boolean loading = false;
    private int pageNumber = 1;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;
    MojAdapter adapter;
    @BindView(R.id.rvSelect)
    RecyclerView rv;


    private Flowable<List<String>> dataFromNetwork(final int page) {
        return Flowable.just(true)
                .delay(2, TimeUnit.SECONDS)
                .map(new Function<Boolean, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull Boolean value) throws Exception {
                        List<String> items = new ArrayList<>();
                        for (int i = 1; i <= 10; i++) {
                            items.add("Item " + (page * 10 + i));
                        }
                        return items;
                    }
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_select);
        ButterKnife.bind(this);

        adapter = new MojAdapter();
        rv.setAdapter(adapter);
        setUpLoadMoreListener();
//        subscribeForData();


    }





    private void setUpLoadMoreListener() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                totalItemCount = layoutManager.getItemCount();
//                lastVisibleItem = layoutManager
//                        .findLastVisibleItemPosition();
                if (!loading
                        && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;
                    paginator.onNext(pageNumber);
                    loading = true;
                }
            }
        });
    }

    /**
     * subscribing for data
     */
//    private void subscribeForData() {
//        Disposable disposable = paginator
//                .onBackpressureDrop()
//                .concatMap(new Function<Integer, Publisher<List<String>>>() {
//                    @Override
//                    public Publisher<List<String>> apply(@NonNull Integer page) throws Exception {
//                        loading = true;
//                        progressBar.setVisibility(View.VISIBLE);
//                        return dataFromNetwork(page);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((Consumer<? super List<String>>) new Consumer<List<String>>() {
//                    @Override
//                    public void accept(@NonNull List<String> items) throws Exception {
//                        adapter.AddItems(items);
//                        adapter.notifyDataSetChanged();
//                        loading = false;
//                        progressBar.setVisibility(View.INVISIBLE);
//                    }
//                });
//
//        compositeDisposable.add(disposable);
//        paginator.onNext(pageNumber);
//    }



}
