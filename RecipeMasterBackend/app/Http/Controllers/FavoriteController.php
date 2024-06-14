<?php

// app/Http/Controllers/FavoriteController.php
namespace App\Http\Controllers;

use App\Models\Favorite;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class FavoriteController extends Controller
{
    public function store(Request $request)
    {
        $request->validate([
            'recipe_id' => 'required|exists:recipes,id',
        ]);

        $favorite = new Favorite();
        $favorite->user_id = Auth::id();
        $favorite->recipe_id = $request->recipe_id;
        $favorite->save();

        return response()->json($favorite, 201);
    }

    public function destroy($id)
    {
        $favorite = Favorite::where('user_id', Auth::id())->where('recipe_id', $id)->first();

        if (!$favorite) {
            return response()->json(['message' => 'Favorite not found'], 404);
        }

        $favorite->delete();

        return response()->json(null, 204);
    }

    public function getFavorites()
    {
        $favorites = Favorite::where('user_id', Auth::id())->pluck('recipe_id');
        return response()->json($favorites, 200);
    }
}
