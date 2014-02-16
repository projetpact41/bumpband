package com.example.bump.actions;

import android.content.Context;

/**
 * Created by jjuulliieenn on 13/01/14.
 */
public interface Transmissible {
    public Transmissible execute(Context context);
    public byte[] toBytes();
}
