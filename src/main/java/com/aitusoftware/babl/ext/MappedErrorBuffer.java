/*
 * Copyright 2019-2020 Aitu Software Limited.
 *
 * https://aitusoftware.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aitusoftware.babl.ext;

import java.io.Closeable;
import java.nio.MappedByteBuffer;
import java.nio.file.Path;

import org.agrona.IoUtil;
import org.agrona.concurrent.UnsafeBuffer;

public final class MappedErrorBuffer implements Closeable
{
    private final MappedByteBuffer mappedByteBuffer;
    private final UnsafeBuffer errorBuffer;

    public MappedErrorBuffer(final Path filePath, final int offset, final int length)
    {
        mappedByteBuffer = IoUtil.mapExistingFile(
            filePath.toFile(), "error-buffer",
            offset, length);
        errorBuffer = new UnsafeBuffer(mappedByteBuffer);
    }

    public UnsafeBuffer errorBuffer()
    {
        return errorBuffer;
    }

    @Override
    public void close()
    {
        IoUtil.unmap(mappedByteBuffer);
    }
}
