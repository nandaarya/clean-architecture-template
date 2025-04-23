package com.github.nandaarya.cleanarchitecturetemplate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AndroidStudioTemplateProviderTest {

    @Test
    fun `getTemplates should return a list with one template`() {
        // Given
        val templateProvider = AndroidStudioTemplateProvider()

        // When
        val templates = templateProvider.getTemplates()

        // Then
        assertTrue(templates.isNotEmpty(), "Templates list should not be empty")
        assertEquals(1, templates.size, "Templates list should have exactly one element")
    }
}