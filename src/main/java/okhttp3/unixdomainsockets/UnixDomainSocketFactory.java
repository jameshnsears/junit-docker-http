/*
 * Copyright (C) 2018 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okhttp3.unixdomainsockets;

import jnr.unixsocket.UnixSocket;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

import javax.net.SocketFactory;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Impersonate TCP-style SocketFactory over UNIX domain sockets.
 */
public final class UnixDomainSocketFactory extends SocketFactory {
    private final File path;

    public UnixDomainSocketFactory(final File path) {
        this.path = path;
    }

    private Socket createUnixDomainSocket() throws IOException {
        final UnixSocketChannel channel = UnixSocketChannel.open();

        return new UnixSocket(channel) {
            private InetSocketAddress inetSocketAddress;

            @Override
            public void connect(final SocketAddress endpoint) throws IOException {
                connect(endpoint, Integer.valueOf(0));
            }

            @Override
            public void connect(final SocketAddress endpoint, final int timeout) throws IOException {
                connect(endpoint, Integer.valueOf(timeout));
            }

            @Override
            public void connect(final SocketAddress endpoint, final Integer timeout) throws IOException {
                this.inetSocketAddress = (InetSocketAddress) endpoint;
                super.connect(new UnixSocketAddress(path), timeout);
            }

            @Override
            public InetAddress getInetAddress() {
                return inetSocketAddress.getAddress(); // TODO(jwilson): fake the remote address?
            }
        };
    }

    @Override
    public Socket createSocket() throws IOException {
        return createUnixDomainSocket();
    }

    @Override
    public Socket createSocket(final String host, final int port) throws IOException {
        return createUnixDomainSocket();
    }

    @Override
    public Socket createSocket(
            final String host, final int port, final InetAddress localHost, final int localPort) throws IOException {
        return createUnixDomainSocket();
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port) throws IOException {
        return createUnixDomainSocket();
    }

    @Override
    public Socket createSocket(
            final InetAddress address, final int port, final InetAddress localAddress, final int localPort) throws IOException {
        return createUnixDomainSocket();
    }
}
