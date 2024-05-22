<?php

namespace App\Http\Controllers;

use App\Models\Comment;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class CommentController extends Controller
{
    public function store(Request $request)
    {
        $request->validate([
            'recipe_id' => 'required|exists:recipes,id',
            'comment' => 'required|string',
        ]);

        $comment = new Comment();
        $comment->user_id = Auth::id();
        $comment->recipe_id = $request->recipe_id;
        $comment->comment = $request->comment;
        $comment->save();

        return response()->json($comment, 201);
    }

    public function update(Request $request, $id)
    {
        $request->validate([
            'comment' => 'required|string',
        ]);

        $comment = Comment::where('user_id', Auth::id())->where('id', $id)->first();

        if (!$comment) {
            return response()->json(['message' => 'Comment not found'], 404);
        }

        $comment->comment = $request->comment;
        $comment->save();

        return response()->json($comment, 200);
    }

    public function destroy($id)
    {
        $comment = Comment::where('user_id', Auth::id())->where('id', $id)->first();

        if (!$comment) {
            return response()->json(['message' => 'Comment not found'], 404);
        }

        $comment->delete();

        return response()->json(null, 204);
    }
}

