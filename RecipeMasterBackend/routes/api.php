<?php

use Illuminate\Support\Facades\Route;

use App\Http\Controllers\RecipeController;
use App\Http\Controllers\FavoriteController;
use App\Http\Controllers\RatingController;
use App\Http\Controllers\CommentController;



    Route::get('recipes', [RecipeController::class, 'index']);
    Route::get('recipes/{id}', [RecipeController::class, 'show']);
    Route::post('recipes', [RecipeController::class, 'store']);
    Route::put('recipes/{id}', [RecipeController::class, 'update']);
    Route::delete('recipes/{id}', [RecipeController::class, 'destroy']);

    Route::post('favorites', [FavoriteController::class, 'store']);
    Route::delete('favorites/{id}', [FavoriteController::class, 'destroy']);

    Route::post('ratings', [RatingController::class, 'store']);
    Route::put('ratings/{id}', [RatingController::class, 'update']);

    Route::post('comments', [CommentController::class, 'store']);
    Route::put('comments/{id}', [CommentController::class, 'update']);
    Route::delete('comments/{id}', [CommentController::class, 'destroy']);
