package ntu.nmh61133637.recyclerview;

public class PlacesList {
    String placeName;
    String placeImageName;

    public String getPlaceName() {
        return placeName;
    }
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public String getPlaceImageName() {
        return placeImageName;
    }
    public void setPlaceImageName(String placeImageName) {
        this.placeImageName = placeImageName;
    }

    public PlacesList(String placeName, String placeImageName) {
        this.placeName = placeName;
        this.placeImageName = placeImageName;
    }
}
