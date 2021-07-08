package com.province.libcacheline.body;

import com.province.libcacheline.utils.MCString;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileBodyBuilder extends BaseBodyBuilder<File> {
    @Override
    public RequestBody convert(File value) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", MCString.randUUID(), RequestBody.create(MediaType.parse("multipart/form-data"), value));
        builder.addPart(body);
        return builder.build();
    }
}
