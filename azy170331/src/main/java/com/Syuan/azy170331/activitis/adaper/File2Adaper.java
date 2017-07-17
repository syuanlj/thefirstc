package com.Syuan.azy170331.activitis.adaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.entity.FileInfo;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.utils.FileTypeUtil;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Video.Thumbnails.MICRO_KIND;

/**
 * Created by sy on 2017/5/12.
 */

public class File2Adaper extends BaseAdapter {
    private List<FileInfo> list;
    private LayoutInflater inflater;
    private Context context;
    private static boolean isFling = false;

    public File2Adaper(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public static void setFling(boolean fling) {
        isFling = fling;
    }

    public List<FileInfo> getList() {
        return list;
    }

    public void add(List<FileInfo> list) {
        this.list = list;
    }

    static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(5 * 1024 * 1024) {
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FileInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHold viewHold;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.layout_file2_lv, null);
            viewHold.cb = (CheckBox) convertView.findViewById(R.id.file2_cb);
            viewHold.iv = (ImageView) convertView.findViewById(R.id.file2_im_icon);
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.file2_tv_name);
            viewHold.tv_date = (TextView) convertView.findViewById(R.id.file2_tv_date);
            viewHold.tv_size = (TextView) convertView.findViewById(R.id.file2_tv_size);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        final FileInfo info = list.get(position);
        viewHold.tv_name.setText(info.getFileName());
        viewHold.tv_date.setText(info.getLastTime());
        viewHold.tv_size.setText(CommonUtil.getFileSize(info.getFileSize()));

        viewHold.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                info.setChecked(isChecked);
            }
        });

        viewHold.cb.setChecked(info.isChecked());
        int id = context.getResources().getIdentifier(info.getIconId(), "mipmap", context.getPackageName());//图片id
        String fileType = FileTypeUtil.getFileIconAndTypeName(info.getFile())[1];//获得文件类型
//        if (!isFling){
//            if (id==0){
//                viewHold.iv.setImageResource(R.mipmap.ic_launcher);
//            }else {
//                viewHold.iv.setImageResource(id);
//            }
//        }else {
//            viewHold.iv.setImageResource(R.mipmap.ic_launcher);
//        }
        if (isFling) {
            viewHold.iv.setImageResource(R.mipmap.ic_launcher);
        } else {
            //从缓存获得当前文件名的图标
            Bitmap bitmap = lruCache.get(info.getFileName());
            if (fileType.equals(FileTypeUtil.TYPE_IMAGE)) {//如果文件类型为图片，获得缩略图为图标
                if (bitmap == null) {
                    //根据图片的觉得路径转为Bitmap类型
                    bitmap = BitmapFactory.decodeFile(info.getFile().getAbsolutePath());
                    //将Bitmap创建成缓存图片
                    bitmap=bitmap.createScaledBitmap(bitmap,40,40,true);
                    lruCache.put(info.getFileName(),bitmap);
                }
                viewHold.iv.setImageBitmap(bitmap);
            }else {
                if (fileType.equals(FileTypeUtil.TYPE_VIDEO)){///如果是影像，获得缩略图
                    if (bitmap==null){
                        bitmap= ThumbnailUtils.createVideoThumbnail(info.getFile().getAbsolutePath(), MICRO_KIND);
                        if (bitmap!=null){//影像可能损坏，不能获得缩略图
                            bitmap=ThumbnailUtils.extractThumbnail(bitmap,40,40,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                        }else {
                            bitmap=BitmapFactory.decodeResource(convertView.getResources(),id);
                            bitmap=bitmap.createScaledBitmap(bitmap,40,40,true);
                            lruCache.put(info.getFileName(),bitmap);
                        }
                    }
                    viewHold.iv.setImageBitmap(bitmap);
                }else {
                    if (id==0){
                        viewHold.iv.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        viewHold.iv.setImageResource(id);
                    }
                }
            }

        }

        return convertView;
    }

    private class ViewHold {
        CheckBox cb;
        ImageView iv;
        TextView tv_name, tv_date, tv_size;
    }
}
