part of 'cinema_list_cubit.dart';

abstract class CinemaListState extends Equatable {
  const CinemaListState();
}

class CinemaListLoading extends CinemaListState {
  @override
  List<Object> get props => [];
}

class CinemaListLoaded extends CinemaListState {
  final List<Movie> movies;

  CinemaListLoaded(this.movies);

  @override
  List<Object> get props => [movies];
}
