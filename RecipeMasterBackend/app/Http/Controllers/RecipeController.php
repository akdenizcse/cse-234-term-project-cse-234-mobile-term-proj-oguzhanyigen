<?php

// app/Http/Controllers/RecipeController.php
namespace App\Http\Controllers;

use App\Models\Recipe;
use Illuminate\Http\Request;

class RecipeController extends Controller
{
    public function index()
    {
        return Recipe::all();
    }

    public function show($id)
    {
        return Recipe::findOrFail($id);
    }

    public function store(Request $request)
    {
        $recipe = Recipe::create($request->all());
        return response()->json($recipe, 201);
    }

    public function update(Request $request, $id)
    {
        $recipe = Recipe::findOrFail($id);
        $recipe->update($request->all());
        return response()->json($recipe, 200);
    }

    public function destroy($id)
    {
        Recipe::destroy($id);
        return response()->json(null, 204);
    }
}

