package io.tstud.paperweight.Model.Local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.tstud.paperweight.Model.Models.IndustryIdentifier;
import io.tstud.paperweight.Model.Models.Offer;
import io.tstud.paperweight.Model.Models.VolumeSeries;

public class CustomTypeConverters {

    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> authors) {
        Gson gson = new Gson();
        String json = gson.toJson(authors);
        return json;
    }

    @TypeConverter
    public static List<IndustryIdentifier> fromIndustryIdentifier(String value) {
        Type listType = new TypeToken<List<IndustryIdentifier>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromIndustryIdentifierList(List<IndustryIdentifier> indentifiers) {
        Gson gson = new Gson();
        String json = gson.toJson(indentifiers);
        return json;
    }

    @TypeConverter
    public static List<VolumeSeries> fromVolumeSeries(String value) {
        Type listType = new TypeToken<List<VolumeSeries>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromVolumeSeriesList(List<VolumeSeries> volumeSeries) {
        Gson gson = new Gson();
        String json = gson.toJson(volumeSeries);
        return json;
    }

    @TypeConverter
    public static List<Offer> fromOffer(String value) {
        Type listType = new TypeToken<List<Offer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromOfferList(List<Offer> offers) {
        Gson gson = new Gson();
        String json = gson.toJson(offers);
        return json;
    }

}
