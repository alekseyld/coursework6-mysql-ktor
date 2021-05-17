import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_practive6_teacher_info/pages/cinema_list_page.dart';

import 'cubit/cubit.dart';
import 'repository/content_repository.dart';

class SimpleBlocObserver extends BlocObserver {

  @override
  void onChange(BlocBase bloc, Change change) {
    print("onChange cubit: $bloc, change: $change");
    super.onChange(bloc, change);
  }
}

void main() {
  Bloc.observer = SimpleBlocObserver();

  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Кинотеатр',
      theme: ThemeData(
        primarySwatch: Colors.brown,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text("Кинотеатр"),
        ),
        body: RepositoryProvider(
          create: (context) => ContentRepository(),
          child: MultiBlocProvider(
            providers: [
              BlocProvider<CinemaListCubit>(
                create: (context) => CinemaListCubit(
                  contentRepository: RepositoryProvider.of<ContentRepository>(context),
                )..fetchCinemaList(),
              ),
            ],
            child: CinemaListPage(),
          ),
        ),
      ),
    );
  }
}