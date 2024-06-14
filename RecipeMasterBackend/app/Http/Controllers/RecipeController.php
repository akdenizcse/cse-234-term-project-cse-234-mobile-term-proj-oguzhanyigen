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
        $request->validate([
            'title' => 'required|string|max:255',
            'image' => 'image|mimes:jpeg,png,jpg,gif,svg|max:2048',
            'ingredients' => 'required|string',
            'instructions' => 'required|string',
        ]);

       /*  // Handle image upload
        $imageName = time() . '.' . $request->image->extension();
        $request->image->move(public_path('images'), $imageName);
 */
        // Merge image path into request data
        $data = $request->all();
       /*  $data['image'] = $imageName; */

        $recipe = Recipe::create($data);

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

