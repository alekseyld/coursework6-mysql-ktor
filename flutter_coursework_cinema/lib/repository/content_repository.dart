import 'dart:convert';

import 'package:flutter_practive6_teacher_info/model/model.dart';
import 'package:flutter_practive6_teacher_info/model/movie.dart';

import 'package:http/http.dart' as http;

const ROOT_URL = "localhost:8080";

class ContentRepository {
  Future<List<Movie>> fetchMovies() async {
    final uri = Uri.http(ROOT_URL, "/movies");

    final response = await http.get(uri);

    Iterable l = jsonDecode(response.body);

    return List<Movie>.from(l.map((model) => Movie.fromJson(model)));
  }

  Future<List<MovieSessionModel>> fetchMovieSession(int movieId) async {
    final uri =
        Uri.http(ROOT_URL, "/movie_sessions", {"movie_id": movieId.toString()});

    final response = await http.get(uri);

    Iterable l = jsonDecode(response.body);

    return List<MovieSessionModel>.from(
        l.map((model) => MovieSessionModel.fromJson(model)));
  }

  Future<void> reserveSeat(int movieSessionId, int seatId) async {
    final uri = Uri.http(ROOT_URL, "/reservate_seat", {
      "seat_id": seatId.toString(),
      "movie_session": movieSessionId.toString(),
    });

    final response = await http.get(uri);
  }
}
