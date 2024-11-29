/*
 * Copyright (c) 2023-2024 Leon Linhart
 * All rights reserved.
 */
package com.osmerion.gradle.jdk.tools.internal

import org.gradle.api.provider.Property
import org.gradle.api.provider.ListProperty

internal fun <T> Property<T>.finalizeAndGet(): T {
    finalizeValue()
    return get()
}

internal fun <T> ListProperty<T>.finalizeAndGet(): List<T> {
    finalizeValue()
    return get()
}