package com.guen.program.todo.api;

import com.guen.common.model.PageRequest;
import com.guen.jwt.security.UserAuthorize;
import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.request.TodoReq;
import com.guen.program.todo.model.response.TodoRes;
import com.guen.program.todo.model.response.TodoSingleRes;
import com.guen.program.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo API")
@UserAuthorize
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @Operation(summary = "todo 목록 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todo 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Page<TodoRes> getTodos(
            @RequestParam(name = "subject", required = false) final String subject,
            final PageRequest pageRequest
    ){
        log.info("TodoController > getTodos");
        return todoService.search(subject, pageRequest.of()).map(TodoRes::new);
    }

    /**
    @GetMapping
    public Page<AccountDto.Res> getAccounts(
            @RequestParam(name = "type") final AccountSearchType type,
            @RequestParam(name = "subject", required = false) final String subject,
            final PageRequest pageRequest
    ) {
        return accountSearchService.search(type, value, pageRequest.of()).map(AccountDto.Res::new);
    }
     **/

    @GetMapping("/{todoId}")
    @Operation(summary = "todo 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todo 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity getTodo(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH) // @ApiParam : Parameter 설명
            @PathVariable(value = "todoId", required = true) String todoId
    ) {
        log.info("TodoController > getTodo");
        Optional<Todo> todo = todoService.findById(todoId);
        //return ResponseEntity.ok().body(new TodoRes(todo.get()));
        return ResponseEntity.ok().body(TodoSingleRes.builder().todo(todo.get()).build());
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "todo 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 생성 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity create(
            @Valid @RequestPart(value = "todoReq") TodoReq todoReq,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        log.info("TodoController > create");
        Todo todo = todoService.save(todoReq,files);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "todo 수정") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 수정 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity update(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) String todoId,
            @Valid @RequestPart(value = "todoReq") TodoReq todoReq,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        log.info("TodoController > update");
        todoService.updateById(todoId,todoReq,files);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "todo 삭제") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 삭제 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity delete(
            @Parameter(description = "todo 아이디",required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) String todoId
    ) {
        log.info("TodoController > delete");
        todoService.remove(todoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}/{completed}")
    @Operation(summary = "todo 완료 여부 설정") // @Operation : API 설명
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "todo 완료 여부 설정 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity updateComplete(
            @Parameter(description = "todo 아이디", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "todoId", required = true) String todoId,
            @Parameter(description = "todo 완료 여부", required = true, in = ParameterIn.PATH)
            @PathVariable(value = "completed", required = true) boolean completed
    ) {
        log.info("TodoController > updateComplete");
        todoService.updateCompleteById(todoId,completed);
        return ResponseEntity.noContent().build();
    }

}
