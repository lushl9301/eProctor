package videoMultiThreadSerializable;

import java.io.Serializable;

public class RecordObject implements Serializable {

    protected static final long serialVersionUID = 1123L;
    public String userId;
    public byte[] imageBytes;

    public RecordObject(String userId, byte[] imageBytes) {
        this.userId = userId;
        this.imageBytes = imageBytes;
    }

    public String getUserId() {
        return this.userId;
    }

    public byte[] getImageBytes() {
        return this.imageBytes;
    }
}
