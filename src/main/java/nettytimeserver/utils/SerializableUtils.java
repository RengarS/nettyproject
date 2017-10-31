package nettytimeserver.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import nettytimeserver.domain.Request;
import nettytimeserver.domain.Response;

public class SerializableUtils {
    private static final RuntimeSchema<Request> REQUEST_RUNTIME_SCHEMA = RuntimeSchema.createFrom(Request.class);
    private static final RuntimeSchema<Response> RESPONSE_RUNTIME_SCHEMA = RuntimeSchema.createFrom(Response.class);

    /**
     * 将字节数组反序列化成对象
     *
     * @param bytes 字节数组
     * @return 反序列化后的对象
     */
    public static <T> T UnSerializableObject(byte[] bytes, Class<?> clz) {
        if (clz == Request.class) {
            Request request = REQUEST_RUNTIME_SCHEMA.newMessage();
            ProtobufIOUtil.mergeFrom(bytes, request, REQUEST_RUNTIME_SCHEMA);
            return (T) request;
        } else if (clz == Response.class) {
            Response response = RESPONSE_RUNTIME_SCHEMA.newMessage();
            ProtobufIOUtil.mergeFrom(bytes, response, RESPONSE_RUNTIME_SCHEMA);
            return (T) response;
        }
        return null;
    }

    /**
     * 将对象序列化成byte数组
     *
     * @param toBeSerial 需要序列化的对象
     * @return 对象序列化后的字节数组
     */
    public static <T> byte[] SerializableObject(T toBeSerial, Class<?> clz) {
        RuntimeSchema runtimeSchema = null;
        if (toBeSerial.getClass() == Request.class) {
            runtimeSchema = REQUEST_RUNTIME_SCHEMA;
        } else if (toBeSerial.getClass() == Response.class) {
            runtimeSchema = RESPONSE_RUNTIME_SCHEMA;
        }
        byte[] bytes = ProtobufIOUtil.toByteArray(toBeSerial, runtimeSchema, LinkedBuffer.allocate(LinkedBuffer
                .DEFAULT_BUFFER_SIZE));
        return bytes;
    }
}
