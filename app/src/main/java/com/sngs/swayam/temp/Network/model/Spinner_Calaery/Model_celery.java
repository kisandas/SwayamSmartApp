package com.sngs.swayam.temp.Network.model.Spinner_Calaery;

public class Model_celery {
    String calry_name;
    int Image_Of_calery;

    public Model_celery(int Image_Of_calery, String calry_nm) {

        this.calry_name = calry_nm;
        this.Image_Of_calery = Image_Of_calery;
    }

    public String getCalry_name() {
        return calry_name;
    }

    public void setCalry_name(String calry_name) {
        this.calry_name = calry_name;
    }

    public int getImage_Of_calery() {
        return Image_Of_calery;
    }

    public void setImage_Of_calery(int image_Of_calery) {
        Image_Of_calery = image_Of_calery;
    }
}
