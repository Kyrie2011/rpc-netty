package cn.wanxh.serialization;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class HessianSerialization implements RpcSerialization{
    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null) throw new NullPointerException();

        byte[] data;

        HessianSerializerOutput hessianOutput;

        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
           hessianOutput = new HessianSerializerOutput(os);
           hessianOutput.writeObject(obj);
           hessianOutput.flush();
           data = os.toByteArray();
        }catch(Exception e){
            throw new SerializationException(e);
        }
        return data;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        if (data == null) throw new NullPointerException();

        T result;

        try(final ByteArrayInputStream is = new ByteArrayInputStream(data)){
            HessianSerializerInput hessianInput = new HessianSerializerInput(is);
            result = (T) hessianInput.readObject(clz);
        }catch(Exception e){
            throw new SerializationException(e);
        }

        return result;
    }
}
