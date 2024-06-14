<?php

use App\Http\Controllers\UserController;
use Illuminate\Support\Facades\Route;

use App\Http\Controllers\RecipeController;
use App\Http\Controllers\FavoriteController;
use App\Http\Controllers\RatingController;
use App\Http\Controllers\CommentController;

Route::post('/auth/register', [UserController::class, 'create'])->name('register');
Route::post('/auth/login', [UserController::class, 'login'])->name('login');
Route::get('/user', [UserController::class,'showAll'])->name('getAll.user');
Route::get('/user/{userId}', [UserController::class,'showUser'])->name('getUserById');


Route::middleware('auth:sanctum')->group(function () {
    Route::get('recipes', [RecipeController::class, 'index']);
    Route::get('recipes/{id}', [RecipeController::class, 'show']);
    Route::post('recipes', [RecipeController::class, 'store']);
    Route::put('recipes/{id}', [RecipeController::class, 'update']);
    Route::delete('recipes/{id}', [RecipeController::class, 'destroy']);

    Route::post('favorites', [FavoriteController::class, 'store']);
    Route::delete('favorites/{id}', [FavoriteController::class, 'destroy']);
    Route::get('favorites', [FavoriteController::class, 'getFavorites']);

    Route::post('ratings', [RatingController::class, 'store']);
    Route::put('ratings/{id}', [RatingController::class, 'update']);

    Route::post('comments', [CommentController::class, 'store']);
    Route::put('comments/{id}', [CommentController::class, 'update']);
    Route::delete('comments/{id}', [CommentController::class, 'destroy']);
});
