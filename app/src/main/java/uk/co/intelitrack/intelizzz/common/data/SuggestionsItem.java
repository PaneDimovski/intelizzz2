package uk.co.intelitrack.intelizzz.common.data;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class SuggestionsItem implements SearchSuggestion {
    public static final Creator<SuggestionsItem> CREATOR = new Creator<SuggestionsItem>() {
        @Override
        public SuggestionsItem createFromParcel(Parcel in) {
            return new SuggestionsItem(in);
        }

        @Override
        public SuggestionsItem[] newArray(int size) {
            return new SuggestionsItem[size];
        }
    };
    private String mName;
    private String mId;

    public SuggestionsItem(String name, String id) {
        this.mName = name;
        this.mId = id;
    }

    public SuggestionsItem(Parcel source) {
        this.mName = source.readString();
        this.mId = source.readString();
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mId);
    }

    public String getId() {
        return mId;
    }

    @Override
    public String getBody() {
        return mName;
    }
}