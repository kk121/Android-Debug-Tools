package com.krishna.devtools.listener;

import java.io.File;
import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */

public interface FilesListListener {
    void onFilesListFetched(List<File> fileList);
}
