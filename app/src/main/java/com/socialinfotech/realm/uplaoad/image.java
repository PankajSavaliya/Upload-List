package com.socialinfotech.realm.uplaoad;


import android.os.Parcel;
import android.os.Parcelable;

public class image {


    /**
     * error : false
     * message : Image uploaded successfully
     * image_name : {"original":"http://web.socialinfotech.com/viva_v2/api/v1/uploads/original/b79df17cc291b66944d61333fcbb2d9a.jpg","large":"http://web.socialinfotech.com/viva_v2/api/v1/uploads/large/b79df17cc291b66944d61333fcbb2d9a.jpg","mini":"http://web.socialinfotech.com/viva_v2/api/v1/uploads/mini/b79df17cc291b66944d61333fcbb2d9a.jpg","image_info":"http://web.socialinfotech.com/viva_v2/api/v1/uploads/image_info/b79df17cc291b66944d61333fcbb2d9a.jpg","thumbnail":"http://web.socialinfotech.com/viva_v2/api/v1/uploads/thumbnail/b79df17cc291b66944d61333fcbb2d9a.jpg"}
     */

    private boolean error;
    private String message;
    /**
     * original : http://web.socialinfotech.com/viva_v2/api/v1/uploads/original/b79df17cc291b66944d61333fcbb2d9a.jpg
     * large : http://web.socialinfotech.com/viva_v2/api/v1/uploads/large/b79df17cc291b66944d61333fcbb2d9a.jpg
     * mini : http://web.socialinfotech.com/viva_v2/api/v1/uploads/mini/b79df17cc291b66944d61333fcbb2d9a.jpg
     * image_info : http://web.socialinfotech.com/viva_v2/api/v1/uploads/image_info/b79df17cc291b66944d61333fcbb2d9a.jpg
     * thumbnail : http://web.socialinfotech.com/viva_v2/api/v1/uploads/thumbnail/b79df17cc291b66944d61333fcbb2d9a.jpg
     */

    private ImageNameEntity image_name;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImage_name(ImageNameEntity image_name) {
        this.image_name = image_name;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ImageNameEntity getImage_name() {
        return image_name;
    }

    public static class ImageNameEntity implements Parcelable {
        private String original;
        private String large;
        private String mini;
        private String image_info;
        private String thumbnail;

        public void setOriginal(String original) {
            this.original = original;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public void setMini(String mini) {
            this.mini = mini;
        }

        public void setImage_info(String image_info) {
            this.image_info = image_info;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getOriginal() {
            return original;
        }

        public String getLarge() {
            return large;
        }

        public String getMini() {
            return mini;
        }

        public String getImage_info() {
            return image_info;
        }

        public String getThumbnail() {
            return thumbnail;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.original);
            dest.writeString(this.large);
            dest.writeString(this.mini);
            dest.writeString(this.image_info);
            dest.writeString(this.thumbnail);
        }

        public ImageNameEntity() {
        }

        protected ImageNameEntity(Parcel in) {
            this.original = in.readString();
            this.large = in.readString();
            this.mini = in.readString();
            this.image_info = in.readString();
            this.thumbnail = in.readString();
        }

        public static final Creator<ImageNameEntity> CREATOR = new Creator<ImageNameEntity>() {
            public ImageNameEntity createFromParcel(Parcel source) {
                return new ImageNameEntity(source);
            }

            public ImageNameEntity[] newArray(int size) {
                return new ImageNameEntity[size];
            }
        };
    }
}