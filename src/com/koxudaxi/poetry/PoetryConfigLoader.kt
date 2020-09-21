package com.koxudaxi.poetry


import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.serviceContainer.AlreadyDisposedException
import com.jetbrains.python.statistics.sdks


/**
 *  This source code is created by @koxudaxi  (Koudai Aono)
 */


class PoetryConfigLoader : StartupActivity {
    override fun runActivity(project: Project) {
        if (ApplicationManager.getApplication().isUnitTestMode) return
        if (project.isDisposed) return
        DumbService.getInstance(project).smartInvokeLater {
            try {
                project.sdks
                        .filterNot { it.isPoetry }
                        .filter { isPoetryFromConfig(project, it) }
                        .forEach { it.isPoetry = true }
            } catch (e: AlreadyDisposedException) {
            }
        }
    }
}
