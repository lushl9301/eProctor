package entity;

import java.io.Serializable;

public class RecordObject implements Serializable {

    protected static final long serialVersionUID = 1123L;
    public String userId;
    public byte[] cameraBytes;
    public byte[] screenBytes

    public RecordObject(String userId, byte[] cameraBytes, byte[] screenBytes) {
        this.userId = userId;
        this.cameraByte = cameraBytes;
        this.screenBytes = screenBytes
    }

    public String getUserId() {
        return this.userId;
    }

    public byte[] getCameraBytes() {
        return this.cameraByte;
    }
    
    public byte[] getScreenBytes() {
    	return this.screenBytes;
    }
    
}
