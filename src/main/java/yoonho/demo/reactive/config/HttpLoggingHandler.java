package yoonho.demo.reactive.config;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;

public class HttpLoggingHandler extends LoggingHandler {

    @Override
    protected String format(ChannelHandlerContext ctx, String event, Object arg) {
        if (arg instanceof ByteBuf) {
            ByteBuf msg = (ByteBuf) arg;
            return msg.toString(StandardCharsets.UTF_8);
        }
        return super.format(ctx, event, arg);
    }
}