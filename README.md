# Job Application Tracker App

## Overview
The **Job Application Tracker** is an Android app built using **Kotlin**, **Jetpack Compose**, and **Room Database**. This app enables users to enter and track their job applications, including application and interview dates. It is designed to offer a simple and user-friendly interface, leveraging the power of modern Android development tools.

## Features
- **Add Job Applications**: Users can input details such as company name, job title, and application date.
- **Track Interview Dates**: Users can enter and monitor the dates of interviews associated with each job application.
- **View and Edit Applications**: Users can view a list of all job applications and edit or delete them as needed.

## Technologies Used
- **Kotlin**: The programming language used to develop the app. [Learn more](https://kotlinlang.org/docs/home.html)
- **Jetpack Compose**: A modern toolkit for building native Android UI. [Learn more](https://developer.android.com/jetpack/compose)
- **Room Database**: A persistence library that provides an abstraction layer over SQLite. [Learn more](https://developer.android.com/training/data-storage/room)
- **Android Jetpack**: A suite of libraries to help developers follow best practices and reduce boilerplate code. [Learn more](https://developer.android.com/jetpack)

## App Architecture
The app follows the MVVM (Model-View-ViewModel) architecture pattern, utilizing the following components:
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way.
- **Repository**: Abstracts access to multiple data sources.
- **Room Database**: Handles local data storage.

## Installation

### Prerequisites
- Android Studio 4.2 or later
- Kotlin 1.5.0 or later

### Clone the Repository
To get started, clone the repository:

```bash
git clone https://github.com/okaraworks/BabyTracking-App.git
