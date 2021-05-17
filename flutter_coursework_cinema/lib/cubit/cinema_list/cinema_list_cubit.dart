import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_practive6_teacher_info/model/movie.dart';
import 'package:flutter_practive6_teacher_info/repository/content_repository.dart';

part 'cinema_list_state.dart';

class CinemaListCubit extends Cubit<CinemaListState> {
  final ContentRepository contentRepository;

  CinemaListCubit({required this.contentRepository}) : super(CinemaListLoading());

  void fetchCinemaList() async {
    if (state is CinemaListLoaded) return;

    final movies = await contentRepository.fetchMovies();

    emit(CinemaListLoaded(movies));

  }

}
