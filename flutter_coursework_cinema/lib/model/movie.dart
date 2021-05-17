import 'package:json_annotation/json_annotation.dart';

part 'movie.g.dart';

@JsonSerializable()
class Movie {
  final int id;
  final String title;
  final String description;
  final int durationMinutes;
  final String director;
  final String genre;
  final String posterUrl;
  final List<String> actors;

  Movie({
    required this.id,
    required this.title,
    required this.description,
    required this.durationMinutes,
    required this.director,
    required this.genre,
    required this.posterUrl,
    required this.actors,
  });

  factory Movie.fromJson(Map<String, dynamic> json) =>
      _$MovieFromJson(json);

  Map<String, dynamic> toJson() => _$MovieToJson(this);
}
