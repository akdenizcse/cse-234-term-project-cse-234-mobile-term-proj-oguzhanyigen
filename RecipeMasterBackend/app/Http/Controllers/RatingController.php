<?php

namespace App\Http\Controllers;

use App\Models\Rating;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class RatingController extends Controller
{
    public function store(Request $request)
    {
        $request->validate([
            'recipe_id' => 'required|exists:recipes,id',
            'rating' => 'required|integer|min:1|max:5',
        ]);

        $rating = new Rating();
        $rating->user_id = Auth::id();
        $rating->recipe_id = $request->recipe_id;
        $rating->rating = $request->rating;
        $rating->save();

        return response()->json($rating, 201);
    }

    public function update(Request $request, $id)
    {
        $request->validate([
            'rating' => 'required|integer|min:1|max:5',
        ]);

        $rating = Rating::where('user_id', Auth::id())->where('recipe_id', $id)->first();

        if (!$rating) {
            return response()->json(['message' => 'Rating not found'], 404);
        }

        $rating->rating = $request->rating;
        $rating->save();

        return response()->json($rating, 200);
    }
}
