/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package mobile.contentstack.tv;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/*
 * Movies class represents video entity with title, description, image thumbs and video url.
 */

public class Movies implements Serializable {
    static final long serialVersionUID = 727566175075960653L;
    static final String BG = "https://image.tmdb.org/t/p/w500/";
    static final String PREVIEW = "https://image.tmdb.org/t/p/w342/";

    private String id;
    private String title;
    private String description;
    private String cardImageUrl;
    private String videoUrl;
    private String tailor;
    private String studio;
    private static LinkedHashMap<String, List<Movies> > MOVIES;

    public Movies() { }

    public Movies(String id, String title, String description, String cardImageUrl, String videoUrl, String tailor, String studio) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cardImageUrl = cardImageUrl;
        this.videoUrl = videoUrl;
        this.tailor = tailor;
        this.studio = studio;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStudio() {
        return studio;
    }

    public String getCardImageUrl() { return cardImageUrl; }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTailor() {
        return tailor;
    }

    static LinkedHashMap<String, List<Movies>> getLinked_movies() { return MOVIES; }

    static void setLinked_movies(LinkedHashMap<String, List<Movies>> linked_movies) { MOVIES = linked_movies; }

    @Override
    public String toString() {
        return "Movies{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cardImageUrl='" + cardImageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", tailor='" + tailor + '\'' +
                ", studio='" + studio + '\'' +
                '}';
    }


}
